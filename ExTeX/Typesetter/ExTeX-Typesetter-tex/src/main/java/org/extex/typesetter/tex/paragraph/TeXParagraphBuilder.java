/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.typesetter.tex.paragraph;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.*;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.typesetter.Badness;
import org.extex.typesetter.Discardable;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.paragraphBuilder.FixedParagraphShape;
import org.extex.typesetter.paragraphBuilder.HangingParagraphShape;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.node.factory.NodeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class implements the paragraph breaking algorithm as used in
 * TeX.
 * <p>
 * The implementation stared as a direct translation of the original sources to
 * Java. This includes the original comments. Thus most of the original comments
 * can still be found in this file.
 * </p>
 *
 * <p>
 * 813. Breaking paragraphs into lines.
 * </p>
 * <p>
 * We come now to what is probably the most interesting algorithm of TeX: the
 * mechanism for choosing the "best possible" breakpoints that yield the
 * individual lines of a paragraph. TeX's line-breaking algorithm takes a
 * given horizontal list and converts it to a sequence of boxes that are
 * appended to the current vertical list. In the course of doing this, it
 * creates a special data structure containing three kinds of records that
 * are not used elsewhere in TeX. Such nodes are created while a paragraph
 * is being processed, and they are destroyed afterwards; thus, the other
 * parts of TeX do not need to know anything about how line-breaking is done.
 * </p>
 * <p>
 * The method used here is based on an approach devised by Michael F. Plass and
 * the author in 1977, subsequently generalized and improved by the same two
 * people in 1980. A detailed discussion appears in SOFTWARE&mdash;Practice
 * &amp; Experience 11 (1981), 1119&ndash;1184, where it is shown that the
 * line-breaking problem can be regarded as a special case of the problem of
 * computing the shortest path in an acyclic network. The cited paper includes
 * numerous examples and describes the history of line breaking as it has been
 * practiced by printers through the ages. The present implementation adds two
 * new ideas to the algorithm of 1980: memory space requirements are
 * considerably reduced by using smaller records for inactive nodes than for
 * active ones, and arithmetic overflow is avoided by using "delta distances"
 * instead of keeping track of the total distance from the beginning of the
 * paragraph to the current point.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
@SuppressWarnings("RedundantThrows")
public class TeXParagraphBuilder implements ParagraphBuilder, LogEnabled {

    /**
     * The field {@code logger} contains the logger.
     */
    private Logger logger;

    /**
     * The field {@code localizer} contains the localizer or {@code null}
     * if none has been set yet.
     */
    private final Localizer localizer = LocalizerFactory.getLocalizer( getClass());

    /**
     * The field {@code nodeFactory} contains the node factory.
     */
    private NodeFactory nodeFactory;

    /**
     * The field {@code options} contains the reference to the context.
     */
    private TypesetterOptions options;

    /**
     * <p>The Count Parameter {@code \adjdemerits}</p>
     */
    private long adjDemerits;

    /**
     * <p>The Count Parameter {@code \brokenpenalty}</p>
     *
     * <p>
     * The parameter {@code \brokenpenalty} contains the penalty which is added
     * if a line ends within an hyphenation point.
     * </p>
     */
    private long brokenPenalty;

    /**
     * <p>The Count Parameter {@code \clubpenalty}</p>
     */
    private long clubPenalty;

    /**
     * <p>The Count Parameter {@code \doublehyphendemerits}</p>
     */
    private long doubleHyphenDemerits;

    /**
     * <p>The Count Parameter {@code \emergencystretch}</p>
     */
    private FixedDimen emergencyStretch;

    /**
     * <p>The CountParameter {@code \exhyphenpenalty}</p>
     */
    private long exHyphenPenalty;

    /**
     * <p>The Parameter {@code \finalhyphendemerits}</p>
     */
    private long finalHyphenDemerits;

    /**
     * <p>The Count Parameter {@code \finalwidowpenalty}</p>
     */
    private long finalWidowPenalty;

    /**
     *  <p>The Count Parameter {@code \hyphenpenalty}</p>
*
     * The parameter {@code \hyphenpenalty} contains the penalty inserted
     * whenever a hyphenation is applied. Thus paragraphs with less hyphenations
     * are preferred over those with more hyphenations.
     */
    private long hyphenPenalty;

    /**
     *  <p>The Count Parameter {@code \interlinepenalty}</p>
*
     * TODO missing documentation
     */
    private long interLinePenalty;

    /**
     *  <p>The Skip Parameter {@code \leftskip}</p>
*
     * <p>
     * The parameter {@code \leftskip} contains the glue which is inserted at
     * the left side of each line in the paragraph. The default is 0&nbsp;pt.
     * </p>
     * <p>
     * This parameter can be used to flash the line to the left side or center
     * the line. Those effects can be achieved in combination with the parameter
     * {@code \rightskip}.
     * </p>
     *
     * <p>Examples</p>

     *
     * <pre class="TeXSample">
     *    \leftskip=0pt plus 2pt minus 2pt  </pre>
     *
     * <pre class="TeXSample">
     *    \leftskip=1fill  </pre>
     *
     */
    private FixedGlue leftSkip;

    /**
     *  <p>The Count Parameter {@code \linepenalty}</p>
*
     * TODO missing documentation
     */
    private long linePenalty;

    /**
     *  <p>The Count Parameter {@code \looseness}</p>
*
     * TODO missing documentation
     */
    private long looseness;

    /**
     *  <p>The Skip Parameter {@code \parfillskip}</p>
* <p>
     * The parameter {@code \parfillskip} contains the glue which is added at
     * the end of each paragraph.
     * </p>
     *
     * <p>Examples</p>

     *
     * <pre class="TeXSample">
     *    \parfillskip=.5ex plus 2pt minus 2pt  </pre>
     *
     */
    private FixedGlue parfillSkip;

    /**
     *  <p>The Count Parameter {@code \pretolerance}</p>
*
     * TODO missing documentation
     */
    private long preTolerance;

    /**
     *
     */
    private int prevGraf;

    /**
     * <p>The Skip Parameter {@code \rightskip}</p>
     * <p>
     * The parameter {@code \rightskip} contains the glue which is inserted at
     * the right side of each line in the paragraph. The default is 0&nbsp;pt.
     * </p>
     * <p>
     * This parameter can be used to flash the line to the right side or center
     * the line. Those effects can be achieved in combination with the parameter
     * {@code \leftskip}.
     * </p>
     *
     * <p>Examples</p>
     *
     * <pre class="TeXSample">
     *    \rightskip=0pt plus 2pt minus 2pt  </pre>
     *
     * <pre class="TeXSample">
     *    \rightskip=1fill  </pre>
     *
     */
    private FixedGlue rightSkip;

    /**
     *  <p>The CountParameter {@code \tolerance}</p>
     *
     * TODO missing documentation
     */
    private long tolerance;

    /**
     * <p>The Count Parameter {@code \tracingparagraphs}</p>
     * <p>
     * TODO missing documentation
     * </p>
     * <p>Examples</p>
     *
     *
     * <pre class="TeXSample">
     *    \tracingparagraphs=1  </pre>
     */
    private boolean tracingParagraphs;

    /**
     * The field {@code active} contains the list of active and delta nodes.
     */
    private final List<Object> active = new ArrayList<Object>();

    /**
     * The field {@code passive} contains the list of potential break points.
     */
    private final List<PassiveNode> passive = new ArrayList<PassiveNode>();

    /**
     * The field {@code parshape} contains the paragraph shape specification.
     * This field is initialized at the beginning of the line breaking if it is
     * {@code null}. At the end of the line breaking it is reset to
     * {@code null}.
     */
    private ParagraphShape parshape = null;

    /**
     * The field {@code fixedParshape} contains the data object used to
     * transport the fixed paragraph shape to the appropriate places. The values
     * stored in it will be overwritten whenever this object will be used for
     * the current paragraph.
     */
    private final FixedParagraphShape fixedParshape = new FixedParagraphShape(
        Dimen.ZERO_PT);

    /**
     * The field {@code hangingParshape} contains the data object used to
     * transport the hanging paragraph shape to the appropriate places. The
     * values stored in it will be overwritten whenever this object will be used
     * for the current paragraph.
     */
    private final HangingParagraphShape hangingParshape =
        new HangingParagraphShape( 0, Dimen.ZERO_PT, Dimen.ZERO_PT );

    /**
     * <p>
     * 661.
     * </p>
     * <p>
     * In order to provide a decent indication of where an overfull or underfull
     * box originated, we use a global variable pack_begin_line that is set
     * nonzero only when hpack is being called by the paragraph builder or the
     * alignment finishing routine.
     * </p>
     * <p>
     * &lt;&lt;Global variables 13>> +::= pack_begin_line: integer; {source
     * file line
     * where the current paragraph or alignment began; a negative value denotes
     * alignment}
     * </p>
     */
    private Locator packBeginLine;

    /**
     * <p>
     * 814.
     * </p>
     * <p>
     * The line_break procedure should be invoked only in horizontal mode; it
     * leaves that mode and places its output into the current vlist of the
     * enclosing vertical mode (or internal vertical mode). There is one
     * explicit parameter: final_widow_penalty is the amount of additional
     * penalty to be inserted before the final line of the paragraph.
     * </p>
     * <p>
     * There are also a number of implicit parameters: The hlist to be broken
     * starts at link(head), and it is nonempty. The value of prev_graf in the
     * enclosing semantic level tells where the paragraph should begin in the
     * sequence of line numbers, in case hanging indentation or \parshape are in
     * use; prev_graf is zero unless this paragraph is being continued after a
     * displayed formula. Other implicit parameters, such as the par_shape_ptr
     * and various penalties to use for hyphenation, etc., appear in eqtb.
     * </p>
     * <p>
     * After line_break has acted, it will have updated the current vlist and
     * the value of prev_graf. Furthermore, the global variable just_box will
     * point to the final box created by line_break, so that the width of this
     * line can be ascertained when it is necessary to decide whether to use
     * above_display_skip or above_display_short_skip before a displayed
     * formula.
     * </p>
     * <p>
     * &lt;&lt;Global variables 13>> +::=
     * </p>
     * <p>
     * just_box: pointer; {the hlist_node for the last line of the new
     * paragraph}
     * </p>
     */
    private NodeList justBox;

    /**
     * 821. The passive node for a given breakpoint contains only four fields:
     *
     * <ul>
     * <li>{@code link} points to the passive node created just before this
     * one, if any,
     * otherwise it is null.</li>
     *
     * <li>{@code cur_break} points to the position of this breakpoint in the
     * horizontal
     * list for the paragraph being broken.</li>
     *
     * <li>{@code prev_break} points to the passive node that should precede
     * this one in an
     * optimal path to this breakpoint.</li>
     *
     * <li>{@code serial} is equal to n if this passive node is the nth one
     * created during
     * the current pass. (This field is used only when printing out detailed
     * statistics about the line-breaking calculations.)</li>
     * </ul>
     *
     * <p>
     * There is a global variable called passive that points to the most
     * recently created passive node. Another global variable, {@code
     * printed_node}, is helps print out the paragraph when detailed
     * information about the line-breaking computation is being displayed.
     * </p>
     *
     * <ul>
     * <li>define passive_node_size=2 {number of words in passive nodes}</li>
     *
     * <li>define cur_break ::= rlink {in passive node, points to position of
     * this
     * breakpoint}</li>
     *
     * <li>define prev_break ::= llink {points to passive node that should
     * precede
     * this one}</li>
     *
     * <li>define serial ::= info {serial number for symbolic identification}
     * </li>
     *
     * <li>&lt;&lt;Global variables 13&gt;&gt; +::=</li>
     *
     * <li>passive: pointer; {most recent node on passive list}</li>
     *
     * <li>printed_node: pointer; {most recent node that has been printed}</li>
     *
     * <li>pass_number: halfword; {the number of passive nodes allocated on this
     * pass}</li>
     * </ul>
     */
    private int printedNode;

    /**
     * active_width: array [1 .. 6] of scaled; {distance from first active node
     * to cur_p}
     */
    private final WideGlue activeWidth = new WideGlue();

    /**
     * cur_active_width: array [1 .. 6] of scaled; {distance from current active
     * node}
     */
    private final WideGlue curActiveWidth = new WideGlue();

    /**
     * background: array [1 .. 6] of scaled; {length of an "empty" line}
     */
    private final WideGlue background = new WideGlue();

    /**
     * break_width: array [1 .. 6] of scaled; {length being computed after
     * current break}
     */
    private final WideGlue breakWidth = new WideGlue();

    /**
     * no_shrink_error_yet: boolean; {have we complained about infinite
     * shrinkage?}
     */
    private boolean noShrinkErrorYet;

    /**
     * The field {@code cur_p} contains the current breakpoint under
     * consideration
     */
    private int curBreak;

    /*
     * 818.
     *
     * The algorithm essentially determines the best possible way to achieve
     * each feasible combination of position, line, and fitness. Thus, it
     * answers questions like, "What is the best way to break the opening part
     * of the paragraph so that the fourth line is a tight line ending at
     * such-and-such a place?" However, the fact that all lines are to be the
     * same length after a certain point makes it possible to regard all
     * sufficiently large line numbers as equivalent, when the looseness
     * parameter is zero, and this makes it possible for the algorithm to save
     * space and time.
     *
     * An "active node" and a "passive node" are created in mem for each
     * feasible breakpoint that needs to be considered. Active nodes are three
     * words long and passive nodes are two words long. We need active nodes
     * only for breakpoints near the place in the paragraph that is currently
     * being examined, so they are recycled within a comparatively short time
     * after they are created.
     */

    /*
     * 820.
     *
     * &lt;&lt;Initialize the special list heads and constant nodes 790>> +::=
     *
     * type(last_active) <-- hyphenated; line_number(last_active) <--
     * max_halfword; subtype(last_active) <-- 0; {the subtype is never examined
     * by the algorithm}
     */

    /**
     * is this our second attempt to break this paragraph?
     */
    private boolean secondPass;

    /*
     * 823.
     *
     * As the algorithm runs, it maintains a set of six delta-like registers for
     * the length of the line following the first active breakpoint to the
     * current position in the given hlist. When it makes a pass through the
     * active list, it also maintains a similar set of six registers for the
     * length following the active breakpoint of current interest. A third set
     * holds the length of an empty line (namely, the sum of \leftskip and
     * \rightskip); and a fourth set is used to create new delta nodes.
     *
     * When we pass a delta node we want to do operations like
     *
     * for k <-- 1 to6 d o cur_active_width[k] <--
     * cur_active_width[k]+mem[q+k].sc; and we want to do this without the
     * overhead of for loops. The do_all_six macro makes such six-tuples
     * convenient.
     *
     * define do_all_six(#) ::= #(1); #(2); #(3); #(4); #(5); #(6)
     */

    /**
     * is this our final attempt to break this paragraph?
     */
    private boolean finalPass;

    /**
     * The field {@code threshold} contains the maximum badness on feasible
     * lines.
     */
    private long threshold;

    /**
     * The field {@code shortfall} is used in badness calculations. shortfall:
     * scaled; {used in badness calculations}
     */
    private final Dimen shortfall = new Dimen();

    /**
     * no_break_yet: boolean; {have we found a feasible break at cur_p?}
     */
    private boolean noBreakYet;

    /*
     * 824.
     *
     * Let's state the principles of the delta nodes more precisely and
     * concisely, so that the following programs will be less obscure. For each
     * legal breakpoint p in the paragraph, we define two quantities &alpha;(p)
     * and &beta;(p) such that the length of material in a line from breakpoint
     * p to breakpoint q is &gamma;+&beta;(q)-&alpha;(p), for some fixed
     * &gamma;. Intuitively, &alpha;(p) and &beta;(q) are the total length of
     * material from the beginning of the paragraph to a point "after" a break
     * at p and to a point "before" a break at q; and &gamma; is the width of an
     * empty line, namely the length contributed by \leftskip and \rightskip.
     *
     * Suppose, for example, that the paragraph consists entirely of alternating
     * boxes and glue skips; let the boxes have widths
     * x<sub>1</sub>&hellip;x<sub>n</sub> and let the skips have widths
     * y<sub>1</sub>&hellip;y<sub>n</sub> , so that the paragraph can be
     * represented by x<sub>1</sub> y<sub>1</sub>&hellip;x<sub>n</sub>
     * y<sub>n</sub> . Let p<sub>i</sub> be the legal breakpoint at
     * y<sub>i</sub> ; then &alpha;(p<sub>i</sub> )=x<sub>1</sub>+y<sub>1</sub>+
     * &hellip; +x<sub>i</sub> +y<sub>i</sub> , and &beta;(p<sub>i</sub> )=
     * x<sub>1</sub>+y<sub>1</sub>+ &hellip; +x<sub>i</sub> . To check this,
     * note that the length of material from p<sub>2</sub> to p<sub>5</sub> ,
     * say, is &gamma;+x<sub>3</sub> +y<sub>3</sub> +x<sub>4</sub>
     * +y<sub>4</sub> +x<sub>5</sub> =&gamma;+&beta;(p<sub>5</sub> )
     * -&alpha;(p<sub>2</sub> ).
     *
     * The quantities &alpha;, &beta;, &gamma; involve glue stretchability and
     * shrinkability as well as a natural width. If we were to compute
     * &alpha;(p) and &beta;(p) for each p, we would need multiple precision
     * arithmetic, and the multiprecise numbers would have to be kept in the
     * active nodes. TeX avoids this problem by working entirely with relative
     * differences or "deltas." Suppose, for example, that the active list
     * contains a<sub>1</sub>&delta;<sub>1</sub>a<sub>2</sub>
     * &delta;<sub>2</sub> a<sub>3</sub> , where the a's are active breakpoints
     * and the &delta;'s are delta nodes. Then
     * &delta;<sub>1</sub>=&alpha;(a<sub>1</sub>)-&alpha;(a<sub>2</sub> ) and
     * &delta;<sub>2</sub> =&alpha;(a<sub>2</sub> )-&alpha;(a<sub>3</sub> ). If
     * the line breaking algorithm is currently positioned at some other
     * breakpoint p, the active<sub>w</sub>idth array contains the value
     * &gamma;+&beta;(p)-&alpha;(a<sub>1</sub>). If we are scanning through the
     * list of active nodes and considering a tentative line that runs from
     * a<sub>2</sub> to p, say, the cur<sub>a</sub>ctive<sub>w</sub>idth array
     * will contain the value &gamma;+&beta;(p)-&alpha;(a<sub>2</sub> ). Thus,
     * when we move from a<sub>2</sub> to a<sub>3</sub> , we want to add
     * &alpha;(a<sub>2</sub> )-&alpha;(a<sub>3</sub> ) to
     * cur<sub>a</sub>ctive<sub>w</sub>idth; and this is just
     * &delta;<sub>2</sub> , which appears in the active list between
     * a<sub>2</sub> and a<sub>3</sub> . The background array contains &gamma;.
     * The break_width array will be used to calculate values of new delta nodes
     * when the active list is being updated.
     */

    /**
     * line_width: scaled; {the current line will be justified to this width}
     */
    private final Dimen lineWidth = new Dimen();

    /**
     * l: halfword; {line number of current active node}
     */
    private int l;

    /*
     * 828.
     *
     * A pointer variable cur_p runs through the given horizontal list as we
     * look for breakpoints. This variable is global, since it is used both by
     * line_break and by its subprocedure try_break.
     *
     * Another global variable called threshold is used to determine the
     * feasibility of individual lines: breakpoints are feasible if there is a
     * way to reach them without creating lines whose badness exceeds threshold.
     * (The badness is compared to threshold before penalties are added, so that
     * penalty values do not affect the feasibility of breakpoints, except that
     * no break is allowed when the penalty is 10000 or more.) If threshold is
     * 10000 or more, all legal breaks are considered feasible, since the
     * badness function specified above never returns a value greater than
     * 10000.
     *
     * Up to three passes might be made through the paragraph in an attempt to
     * find at least one set of feasible breakpoints. On the first pass, we have
     * threshold=pretolerance and second_pass=final_pass= false. If this pass
     * fails to find a feasible solution, threshold is set to tolerance,
     * second_pass is set true, and an attempt is made to hyphenate as many
     * words as possible. If that fails too, we add emergency_stretch to the
     * background stretchability and set final_pass=true.
     */

    /**
     * artificial_demerits: boolean; {has d been forced to zero?}
     */
    private boolean artificialDemerits;

    /**
     * prev_r: pointer; {stays a step behind r}
     */
    private int prevR;

    /**
     * fit_class: very_loose_fit .. tight_fit; {possible fitness class of test
     * line}
     */
    private Fitness fitClass;

    private boolean breakType;

    /**
     * prev_prev_r: pointer; {a step behind prev_r, if type(prev_r)=delta_node}
     */
    private int prevPrevR;

    /*
     * 829.
     *
     * The heart of the line-breaking procedure is `try_break', a subroutine
     * that tests if the current breakpoint cur_p is feasible, by running
     * through the active list to see what lines of text can be made from active
     * nodes to cur_p. If feasible breaks are possible, new break nodes are
     * created. If cur_p is too far from an active node, that node is
     * deactivated.
     *
     * The parameter pi to try_break is the penalty associated with a break at
     * cur_p; we have pi=eject_penalty if the break is forced, and
     * pi=inf_penalty if the break is illegal.
     *
     * The other parameter, break_type, is set to hyphenated or unhyphenated,
     * depending on whether or not the current break is at a disc_node. The end
     * of a paragraph is also regarded as `hyphenated'; this case is
     * distinguishable by the condition cur_p=null.
     *
     * define copy_to_cur_active(#) ::= cur_active_width[#] <-- active_width[#]
     *
     * define deactivate=60 {go here when node r should be deactivated}
     *
     * &lt;&lt;Declare subprocedures for line_break 826>> +::=
     */

    /**
     * old_l: halfword; {maximum line number in current equivalence class of
     * lines}
     */
    private long oldL;

    /**
     * more than a billion demerits
     */
    private static final int AWFUL_BAD = 0x3FFFFFFF;

    /**
     * best total demerits known for current line class and position, given the
     * fitness
     */
    private final long[] minimalDemerits = new long[ 4 ];

    /**
     * best total demerits known for current line class and position
     */
    private long minimumDemerits;

    /*
     * f: internal_font_number; {used in character width calculation}
     */

    /**
     * how to achieve minimal_demerits
     */
    private final PassiveNode[] bestPlace = new PassiveNode[ 4 ];

    /**
     * corresponding line number
     */
    private final int[] bestPlaceLine = new int[ 4 ];

    /**
     * disc_width: scaled; {the length of discretionary material preceding a
     * break}
     */
    private final Dimen discretionaryWidth = new Dimen();

    /*
     * 847.
     *
     * The length of lines depends on whether the user has specified \parshape
     * or \hangindent. If par_shape_ptr is not null, it points to a (2n+1)-word
     * record in mem, where the info in the first word contains the value of n,
     * and the other 2n words contain the left margins and line lengths for the
     * first n lines of the paragraph; the specifications for line n apply to
     * all subsequent lines. If par_shape_ptr=null, the shape of the paragraph
     * depends on the value of n=hang_after; if n >= 0, hanging indentation
     * takes place on lines n+1, n+2, &hellip;, otherwise it takes place on
     * lines 1, &hellip;, |n|. When hanging indentation is active, the left
     * margin is hang_indent, if hang_indent >= 0, else it is 0; the line length
     * is hsize-|hang_indent|. The normal setting is par_shape_ptr=null,
     * hang_after=1, and hang_indent=0. Note that if hang_indent=0, the value of
     * hang_after is irrelevant.
     *
     */

    // <<Global variables 13>> +::=
    // easy_line: halfword; {line numbers > easy_line are equivalent in break
    // nodes}
    // last_special_line: halfword;
    // {line numbers > last_special_line all have the same width}
    // first_width: scaled;
    // {the width of all lines =< last_special_line, if no \parshape has been
    // specified}
    // second_width: scaled; {the width of all lines > last_special_line}
    // first_indent: scaled; {left margin to go with first_width}
    // second_indent: scaled; {left margin to go with second_width}
    private long easyLine;

    /**
     * The field {@code D_7230584} contains the constant for comparison.
     */
    private static final Dimen D_7230584 = new Dimen( 7230584 );

    /**
     * is node cur_p outside a formula?
     */
    private boolean autoBreaking;

    /*
     * 833.
     *
     * As we consider various ways to end a line at cur_p, in a given line
     * number class, we keep track of the best total demerits known, in an array
     * with one entry for each of the fitness classifications. For example,
     * minimal_demerits[tight_fit] contains the fewest total demerits of
     * feasible line breaks ending at cur_p with a tight_fit line;
     * best_place[tight_fit] points to the passive node for the break before
     * cur_p that achieves such an optimum; and best_pl_line[tight_fit] is the
     * line_number field in the active node corresponding to
     * best_place[tight_fit]. When no feasible break sequence is known, the
     * minimal_demerits entries will be equal to awful_bad, which is 2^{30} - 1.
     * Another variable, minimum_demerits, keeps track of the smallest value in
     * the minimal_demerits array.
     *
     * &lt;&lt;Global variables 13>> +::=
     *
     * minimal_demerits: array [very_loose_fit .. tight_fit] of integer; {best
     * total demerits known for current line class and position, given the
     * fitness}
     *
     * minimum_demerits: integer; {best total demerits known for current line
     * class and position}
     *
     * best_place: array [very_loose_fit .. tight_fit] of pointer; {how to
     * achieve minimal_demerits}
     *
     * best_pl_line: array [very_loose_fit .. tight_fit] of halfword;
     * {corresponding line number}
     */

    /**
     * helps to determine when glue nodes are breakpoints
     */
    private int prevP;

    /**
     * miscellaneous nodes of temporary interest
     */
    private int r;

    /**
     * The field {@code visitor} contains the node visitor for the inner switch
     * on the node types.
     */
    private final NodeVisitor<Object, NodeList> visitor =
        new NodeVisitor<Object, NodeList>() {

            @Override
            public Object visitAdjust( AdjustNode node, NodeList value ) {

                return null;
            }

            @Override
            public Object visitAfterMath( AfterMathNode node, NodeList value )
                throws GeneralException {

                autoBreaking = true;
                kernBreak( value );
                return null;
            }

            @Override
            public Object visitAlignedLeaders( AlignedLeadersNode node,
                                               NodeList value )
                throws GeneralException {

                // confusion("paragraph")
                throw new HelpingException( localizer, "Panic.Paragraph" );
            }

            @Override
            public Object visitBeforeMath( BeforeMathNode node,
                                           NodeList value )
                throws GeneralException {

                autoBreaking = false;
                kernBreak( value );
                return null;
            }

            @Override
            public Object visitCenteredLeaders( CenteredLeadersNode node,
                                                NodeList value )
                throws GeneralException {

                // confusion("paragraph")
                throw new HelpingException( localizer, "Panic.Paragraph" );
            }

            @Override
            public Object visitChar( CharNode node, NodeList value )
                throws GeneralException {

                // begin if is_char_node(cur_p) then
                // <<Advance (c)cur_p to the node following the present
                // string of
                // characters 867>>;
                Node n = advanceToNonChar( value );
                return n.visit( this, value );
            }

            @Override
            public Object visitDiscretionary( DiscretionaryNode node,
                                              NodeList nodes )
                throws GeneralException {

                // disc_node: <<Try to break after a discretionary fragment,
                // then
                // goto done5 869>>;
                /*
                 * 869.
                 *
                 * The following code knows that discretionary texts contain
                 * only character nodes, kern nodes, box nodes, rule nodes,
                 * and ligature nodes.
                 *
                 * This code is used in section 866.
                 */

                // <<Try to break after a discretionary fragment, then goto
                // done5 869>> ::=
                // begin s <-- pre_break(cur_p);
                NodeList nl = node.getPreBreak();
                // disc_width <-- 0;
                discretionaryWidth.set( 0 );
                // if s=null then
                if( nl == null || nl.size() == 0 ) {
                    // try_break(ex_hyphen_penalty,hyphenated)
                    tryBreak( nodes, exHyphenPenalty, true );
                    // else begin
                }
                else {
                    // repeat
                    // <<Add the width of node s to disc_width 870>>;
                    // s <-- link(s);
                    // until s=null;
                    discretionaryWidth.add( node.getWidth() );
                    // act_width <-- act_width+disc_width;
                    activeWidth.add( discretionaryWidth );
                    // try_break(hyphen_penalty,hyphenated);
                    tryBreak( nodes, hyphenPenalty, true );
                    // act_width <-- act_width-disc_width;
                    activeWidth.subtract( discretionaryWidth );
                    // end ;
                }
                // r <-- replace_count(cur_p);
                // s <-- link(cur_p);
                NodeList s = node.getNoBreak();
                // while r > 0 do
                // begin <<Add the width of node s to act_width 871>>;
                // decr(r);
                // s <-- link(s);
                // end ;
                if( s != null ) {
                    activeWidth.add( s.getWidth() );
                }
                // prev_p <-- cur_p;
                prevP = curBreak;
                // cur_p <-- s;
                curBreak++;
                // goto done5;
                return Boolean.TRUE;
                // end
            }

            @Override
            public Object visitExpandedLeaders( ExpandedLeadersNode node,
                                                NodeList value )
                throws GeneralException {

                // confusion("paragraph")
                throw new HelpingException( localizer, "Panic.Paragraph" );
            }

            @Override
            public Object visitGlue( GlueNode glue, NodeList nodes )
                throws GeneralException {

                // glue_node: begin <<If node cur_p is a legal breakpoint,
                // call try_break; then update the active widths by
                // including the glue in glue_ptr(cur_p) 868>>;
                /*
                 * 868.
                 *
                 * When node cur_p is a glue node, we look at prev_p to see
                 * whether or not a breakpoint is legal at cur_p, as
                 * explained above.
                 *
                 * This code is used in section 866.
                 */

                // <<If node cur_p is a legal breakpoint, call try_break;
                // then update the active widths by including the glue in
                // glue_ptr(cur_p) 868>> ::= if auto_breaking then
                if( autoBreaking ) {
                    Node previousNode = nodes.get( prevP );
                    // begin if is_char_node(prev_p) then
                    if( previousNode instanceof CharNode ) {
                        // try_break(0,unhyphenated)
                        tryBreak( nodes, 0, false );
                        // else if precedes_break(prev_p) then
                    }
                    else if( !(previousNode instanceof Discardable) ) {
                        // try_break(0,unhyphenated)
                        tryBreak( nodes, 0, false );
                        // else if (type(prev_p)=kern_node) &&
                        // (subtype(prev_p) != explicit) then
                    }
                    else if( previousNode instanceof KernNode
                        && !(previousNode instanceof ExplicitKernNode) ) {
                        // try_break(0,unhyphenated);
                        tryBreak( nodes, 0, false );
                        // end ;
                    }
                }
                // check_shrinkage(glue_ptr(cur_p));
                checkShrinkage( glue.getSize() );
                // q <-- glue_ptr(cur_p);
                FixedGlue g = glue.getSize();
                // act_width <-- act_width+width(q);
                // active_width[2+stretch_order(q)] <-- active_width[2+
                // stretch_order(q)]+stretch(q);
                // active_width[6] <-- active_width[6]+shrink(q)
                activeWidth.add( g );

                // - - - -
                // if second_pass && auto_breaking then
                if( secondPass && autoBreaking ) {
                    // <<Try to hyphenate the following word 894>>;
                    hyphenateFollowingWord( nodes, curBreak );
                }

                // end ;
                return null;
            }


            @Override
            public Object visitHorizontalList( HorizontalListNode node,
                                               NodeList value )
                throws GeneralException {

                activeWidth.add( node.getWidth() );
                return null;
            }


            @Override
            public Object visitInsertion( InsertionNode node, NodeList value )
                throws GeneralException {

                return null;
            }


            @Override
            public Object visitKern( KernNode node, NodeList value )
                throws GeneralException {

                // kern_node: if subtype(cur_p)=explicit then
                if( node instanceof ExplicitKernNode ) {
                    // kern_break
                    kernBreak( value );
                    // else act_width <-- act_width+width(cur_p);
                }
                else {
                    activeWidth.add( node.getWidth() );
                }
                return null;
            }


            @Override
            public Object visitLigature( LigatureNode node, NodeList value )
                throws GeneralException {

                // ligature_node: begin f <-- font(lig_char(cur_p));
                // act_width <-- act_width+char_width(f)(char_info(f)(
                // character(lig_char(cur_p))));

                activeWidth.add( node.getWidth() );

                // end ;
                return null;
            }


            @Override
            public Object visitMark( MarkNode node, NodeList value )
                throws GeneralException {

                return null;
            }


            @Override
            public Object visitPenalty( PenaltyNode node, NodeList value )
                throws GeneralException {

                tryBreak( value, (int) node.getPenalty(), false );
                return null;
            }


            @Override
            public Object visitRule( RuleNode node, NodeList value )
                throws GeneralException {

                activeWidth.add( node.getWidth() );
                return null;
            }


            @Override
            public Object visitSpace( SpaceNode node, NodeList value )
                throws GeneralException {

                return visitGlue( node, value );
            }


            @Override
            public Object visitVerticalList( VerticalListNode node,
                                             NodeList value )
                throws GeneralException {

                activeWidth.add( node.getWidth() );
                return null;
            }


            @Override
            public Object visitVirtualChar( VirtualCharNode node,
                                            NodeList value )
                throws GeneralException {

                return visitChar( node, value );
            }


            @Override
            public Object visitWhatsIt( WhatsItNode node, NodeList value )
                throws GeneralException {

                // whatsit_node: <<Advance (p)past a whatsit node in the (l)
                // line_break loop 1362>>;
                /*
                 * 1362.
                 *
                 * This code is used in section 866.
                 *
                 * define adv_past(#) ::= if subtype(#)=language_node then
                 * begin cur_lang <-- what_lang(#); l_hyf <-- what_lhm(#);
                 * r_hyf <-- what_rhm(#); end
                 *
                 * &lt;&lt;Advance (p)past a whatsit node in the (l)line_break
                 * loop 1362>> ::= adv_past(cur_p)
                 */
                // - - -
                return null;
            }

        };

    /*
     * 870.
     *
     * This code is used in section 869. <<Add the width of node s to disc_width
     * 870>> ::=
     */
    // if is_char_node(s) then
    // begin f <-- font(s);
    // disc_width <-- disc_width+char_width(f)(char_info(f)(
    // character(s)));
    // end
    // else case type(s) of
    // ligature_node: begin f <-- font(lig_char(s));
    // disc_width <-- disc_width+char_width(f)(char_info(f)(
    // character(lig_char(s))));
    // end ;
    // hlist_node,vlist_node,rule_node,kern_node:
    // disc_width <-- disc_width+width(s);
    // othercases confusion("disc3")
    // endcases
    /*
     * 871.
     *
     * This code is used in section 869.
     */
    // <<Add the width of node s to act_width 871>> ::=
    // if is_char_node(s) then
    // begin f <-- font(s);
    // act_width <-- act_width+char_width(f)(char_info(f)(
    // character(s)));
    // end
    // else case type(s) of
    // ligature_node: begin f <-- font(lig_char(s));
    // act_width <-- act_width+char_width(f)(char_info(f)(
    // character(lig_char(s))));
    // end ;
    // hlist_node,vlist_node,rule_node,kern_node:
    // act_width <-- act_width+width(s);
    // othercases confusion("disc4")
    // endcases
    /**
     * 872.
     * <p>
     * The forced line break at the paragraph's end will reduce the list of
     * breakpoints so that all active nodes represent breaks at cur_p=null. On
     * the first pass, we insist on finding an active node that has the correct
     * "looseness." On the final pass, there will be at least one active node,
     * and we will match the desired looseness as well as we can.
     * <p>
     * The global variable best_bet will be set to the active node for the best
     * way to break the paragraph, and a few other variables are used to help
     * determine what is best.
     * <p>
     * &lt;&lt;Global variables 13>> +::=
     * <p>
     * best_bet: pointer; {use this passive node and its predecessors}
     * <p>
     * line_diff: integer; {the difference between the current line number and
     * the optimum best_line}
     */
    private ActiveNode bestBet;

    /**
     * fewest_demerits: integer; {the demerits associated with best_bet}
     */
    private long fewestDemerits;

    /**
     * best_line: halfword; {line number following the last line of the new
     * paragraph}
     */
    private int bestLine;

    /**
     * actual_looseness: integer; {the difference between line_number(best_bet)
     * and the optimum best_line}
     */
    private long actualLooseness;

    private boolean discBreak;

    /*
     * 839.
     *
     * When cur_p is a discretionary break, the length of a line "from cur_p to
     * cur_p" has to be defined properly so that the other calculations work
     * out. Suppose that the pre-break text at cur_p has length l_0, the
     * post-break text has length l_1, and the replacement text has length l.
     * Suppose also that q is the node following the replacement text. Then
     * length of a line from cur_p to q will be computed as
     * &gamma;+&beta;(q)-&alpha;(cur _p), where &beta;(q)=&beta;(cur_p)-l_0+l.
     * The actual length will be the background plus l_1, so the length from
     * cur_p to cur_p should be &gamma;+l_0+l_1-l. If the post-break text of the
     * discretionary is empty, a break may also discard q; in that unusual case
     * we subtract the length of q and any other nodes that will be discarded
     * after the discretionary break.
     *
     * The value of l_0 need not be computed, since line_break will put it into
     * the global variable disc_width before calling try_break.
     *
     */

    /**
     * post_disc_break: boolean; {and did it have a nonempty post-break part?}
     */
    private NodeList postDiscBreak;


    public TeXParagraphBuilder() {

    }

    /**
     * 867.
     * <p>
     * The code that passes over the characters of words in a paragraph is part
     * of TeX's inner loop, so it has been streamlined for speed. We
     * use the fact that `\parfillskip' glue appears at the end of each
     * paragraph; it is therefore unnecessary to check if link(cur_p)=null when
     * cur_p is a character node.
     * <p>
     * &lt;&lt;Advance (c)cur_p to the node following the present string of
     * characters
     * 867&gt;&gt; ::=
     * <p>
     * This code is used in section 866.
     *
     * @param nodes the node list for the paragraph to break
     * @return the next non-char node
     */
    private Node advanceToNonChar( NodeList nodes ) {

        Node node = nodes.get( curBreak );

        // begin prev_p <-- cur_p;
        prevP = curBreak;

        do {
            // repeat f <-- font(cur_p);
            // act_width <--
            // act_width+char_width(f)(char_info(f)(character(cur_p)));
            activeWidth.add( node.getWidth() );
            // cur_p <-- link(cur_p);
            curBreak++;

            node = nodes.get( curBreak );
            // until not is_char_node(cur_p);
        } while( node instanceof CharNode );

        // end
        return node;
    }

    /*
     * 842.
     *
     * This code is used in section 840.
     */

    /**
     * 890.
     * <p>
     * Penalties between the lines of a paragraph come from club and widow
     * lines, from the inter_line_penalty parameter, and from lines that end at
     * discretionary breaks. Breaking between lines of a two-line paragraph gets
     * both club-line and widow-line penalties. The local variable pen will be
     * set to the sum of all relevant penalties for the current line, except
     * that the final line is never penalized.
     * <p>
     * This code is used in section 880.
     * <p>
     * &lt;&lt;Append a penalty node, if a nonzero penalty is appropriate
     * 890&gt;&gt; ::=
     *
     * @param line    the current line
     * @param curLine the current line number
     */
    private void appendPenalty( NodeList line, long curLine ) {

        long pen;

        // if cur_line+1 != best_line then
        if( curLine + 1 != bestLine ) {
            // begin pen <-- inter_line_penalty;
            pen = interLinePenalty;
            // if cur_line=prev_graf+1 then
            if( curLine == prevGraf + 1 ) {
                // pen <-- pen+club_penalty;
                pen += clubPenalty;
            }
            // if cur_line+2=best_line then
            if( curLine + 2 == bestLine ) {
                // pen <-- pen+final_widow_penalty;
                pen += finalWidowPenalty;
            }
            // if disc_break then
            if( discBreak ) {
                // pen <-- pen+broken_penalty;
                pen += brokenPenalty;
            }
            // if pen != 0 then
            if( pen != 0 ) {
                // begin r <-- new_penalty(pen);
                // link(tail) <-- r;
                // tail <-- r;
                line.add( new PenaltyNode( pen ) );
                // end ;
            }
            // end
        }
    }

    /**
     * 853.
     * <p>
     * Shrinkability is never infinite in a paragraph; we can shrink the line
     * from r to cur_p by at most cur_active_width[6].
     * </p>
     * <p>
     * This code is used in section 851.
     * </p>
     * <p>
     * &lt;&lt;Set the value of b to the badness for shrinking the line, and
     * compute the corresponding fit_class 853>> ::=
     * </p>
     *
     * @return ...
     */
    private int badnessForShrinking() {

        // begin if -shortfall > cur_active_width[6] then
        GlueComponent minusShortfall = new GlueComponent( shortfall );
        minusShortfall.negate();
        int badness;
        if( minusShortfall.gt( curActiveWidth.getShrink() ) ) {
            // b <-- inf_bad+1
            badness = Badness.INF_BAD + 1;
            // else b <-- badness(-shortfall,cur_active_width[6]);
        }
        else {
            badness =
                Badness.badness( minusShortfall.getValue(), curActiveWidth
                    .getShrink().getValue() );
        }
        // if b > 12 then
        // fit_class <-- tight_fit else fit_class <-- decent_fit;
        fitClass = (badness > 12 ? Fitness.TIGHT : Fitness.DECENT);
        // end
        return badness;
    }

    /**
     * 852.
     * <p>
     * When a line must stretch, the available stretchability can be found in
     * the subarray cur_active_width[2 .. 5], in units of points, fil, fill, and
     * filll.
     * </p>
     * <p>
     * The present section is part of TeX's inner loop, and it is most often
     * performed when the
     * badness is infinite; therefore it is worth while to make a quick test for
     * large width excess and small stretchability, before calling the badness
     * subroutine.
     * </p>
     * <p>
     * This code is used in section 851.
     * </p>
     * <p>
     * &lt;&lt;Set the value of b to the badness for stretching the line, and
     * compute
     * the corresponding fit_class 852>> ::=
     * </p>
     *
     * @return ...
     */
    private int badnessForStretching() {

        // if (cur_active_width[3] != 0) || (cur_active_width[4] != 0) || (
        // cur_active_width[5] != 0) then
        FixedGlueComponent stretch = curActiveWidth.getStretch();
        // begin b <-- 0;
        int badness;
        if( stretch.getOrder() > 0 ) {
            // fit_class <-- decent_fit; {infinite stretch}
            fitClass = Fitness.DECENT;
            // end
        }
        else if( shortfall.gt( D_7230584 ) ) {
            // else begin if shortfall > 7230584 then
            // if cur_active_width[2] < 1663497 then
            if( stretch.getValue() < 1663497 ) {
                // begin b <-- inf_bad;
                badness = Badness.INF_BAD;
                // fit_class <-- very_loose_fit;
                fitClass = Fitness.VERY_LOOSE;
                // goto done1;
                return badness;
                // end ;
            }
        }
        // b <-- badness(shortfall,cur_active_width[2]);
        badness = Badness.badness( shortfall.getValue(), stretch.getValue() );
        // if b > 12 then
        // if b > 99 then
        // fit_class <-- very_loose_fit
        // else fit_class <-- loose_fit
        // else fit_class <-- decent_fit;
        if( badness <= 12 ) {
            fitClass = Fitness.DECENT;
        }
        else if( badness > 99 ) {
            fitClass = Fitness.VERY_LOOSE;
        }
        else {
            fitClass = Fitness.LOOSE;
        }
        // done1: end
        return badness;
    }

    @Override
    public NodeList build( HorizontalListNode nodes )
        throws TypesetterException {

        if( nodes.size() == 0 ) {
            return new VerticalListNode();
        }
        adjDemerits = options.getCountOption( "adjdemerits" ).getValue();
        clubPenalty = options.getCountOption( "clubpenalty" ).getValue();
        brokenPenalty = options.getCountOption( "brokenpenalty" ).getValue();
        doubleHyphenDemerits =
            options.getCountOption( "doublehyphendemerits" ).getValue();
        emergencyStretch = options.getDimenOption( "emergencystretch" );
        exHyphenPenalty = options.getCountOption( "exhyphenpenalty" )
                                 .getValue();
        finalHyphenDemerits =
            options.getCountOption( "finalhyphendemerits" ).getValue();
        finalWidowPenalty =
            options.getCountOption( "finalwidowpenalty" ).getValue();
        hyphenPenalty = options.getCountOption( "hyphenpenalty" ).getValue();
        interLinePenalty =
            options.getCountOption( "interlinepenalty" ).getValue();
        leftSkip = options.getGlueOption( "leftskip" );
        linePenalty = options.getCountOption( "linepenalty" ).getValue();
        looseness = options.getCountOption( "looseness" ).getValue();
        parfillSkip = options.getGlueOption( "parfillskip" );
        preTolerance = options.getCountOption( "pretolerance" ).getValue();
        prevGraf = (int) options.getCountOption( "prevgraf" ).getValue();
        rightSkip = options.getGlueOption( "rightskip" );
        tolerance = options.getCountOption( "tolerance" ).getValue();
        tracingParagraphs =
            options.getCountOption( "tracingparagraphs" ).gt( Count.ZERO );
        prepareParshape();

        NodeList result;
        Locator modeLine = null;

        try {
            /*
             * 815.
             *
             * Since line_break is a rather lengthy procedure---sort of a small
             * world unto itself---we must build it up little by little,
             * somewhat more cautiously than we have done with the simpler
             * procedures of TeX. Here is the general outline.
             */
            // label done,done1,done2,done3,done4,done5,continue;
            // var <<Local variables for line breaking 862>>
            // pack_begin_line <-- mode_line; //this is for over/underfull box
            // messages
            packBeginLine = modeLine;
            // <<Get ready to start line breaking 816>>;
            getReadyToStartLineBreaking( nodes );
            // <<Find optimal breakpoints 863>>;
            findOptimalBreakpoints( nodes );
            // <<Break the paragraph at the chosen breakpoints, justify the
            // resulting lines to the correct widths, and append them to
            // the current vertical list 876>>;
            result = postLineBreak( nodes );
            // <<Clean up the memory by removing the break nodes 865>>;
            cleanUpTheMemory();

            packBeginLine = null;

            // - - -

            options.setCountOption( "prevgraf", prevGraf );

        } catch( TypesetterException e ) {
            throw e;
        } catch( GeneralException e ) {
            throw new TypesetterException( e );
        }

        options.setParshape( null );

        return result;
    }

    /**
     * 866.
     * <p>
     * Here is the main switch in the line_break routine, where legal breaks are
     * determined. As we move through the hlist, we need to keep the active
     * _width array up to date, so that the badness of individual lines is
     * readily calculated by try_break. It is convenient to use the short name
     * act_width for the component of active width that represents real width as
     * opposed to glue.
     * </p>
     * <p>
     * define act_width ::= active_width[1] {length from first active node to
     * current node}
     * </p>
     * <p>
     * This code is used in section 863.
     * </p>
     *
     * @param nodes the node list for the paragraph to break
     * @throws GeneralException in case of an error
     */
    private void callTryBreak( NodeList nodes ) throws GeneralException {

        // begin if is_char_node(cur_p) then
        // <<Advance (c)cur_p to the node following the present string of
        // characters 867>>;

        // case type(cur_p) of hlist_node,vlist_node,rule_node:
        // act_width <-- act_width+width(cur_p);
        // whatsit_node: <<Advance (p)past a whatsit node in the (l)
        // line_break loop 1362>>;
        // glue_node: begin <<If node cur_p is a legal
        // breakpoint, call try_break; then update the active widths by
        // including the
        // glue in glue_ptr(cur_p) 868>>;
        // if second_pass && auto_breaking then
        // <<Try to hyphenate the following word 894>>;
        // end ;
        // kern_node: if subtype(cur_p)=explicit then
        // kern_break
        // else act_width <-- act_width+width(cur_p);
        // ligature_node: begin f <-- font(lig_char(cur_p));
        // act_width <-- act_width+char_width(f)(char_info(f)(
        // character(lig_char(cur_p))));
        // end ;
        // disc_node: <<Try to break after a discretionary fragment, then
        // goto done5 869>>;
        // math_node: begin auto_breaking <-- (subtype(cur_p)=after);
        // kern_break;
        // end ;
        // penalty_node: try_break(penalty(cur_p), unhyphenated);
        // mark_node,ins_node,adjust_node: do_nothing;
        // do nothing
        // othercases confusion("paragraph")
        // endcases ;

        if( nodes.get( curBreak ).visit( visitor, nodes ) == null ) {
            // prev_p <-- cur_p;
            prevP = curBreak;
            // cur_p <-- link(cur_p);
            curBreak++;
        }

        // done5: end
    }

    /*
     * 849.
     *
     * This code is used in section 848.
     */

    /**
     * 882.
     * <p>
     * This code is used in section 881.
     * <p>
     * &lt;&lt;Change discretionary to compulsory and set disc_break <-- true
     * 882>>
     * ::=
     *
     * @param node the current node
     * @param line the target line
     */
    private void changeDiscretionary( DiscretionaryNode node, NodeList line ) {

        // begin t <-- replace_count(q);
        // <<Destroy the t nodes following q, and make r point to the following
        // node 883>>;
        sub883();

        NodeList postBreak = node.getPostBreak();
        // if post_break(q) != null then
        if( postBreak != null && postBreak.size() > 0 ) {
            // <<Transplant the post-break list 884>>;
            transplantPostBreakList( postBreak );
        }
        NodeList preBreak = node.getPreBreak();
        // if pre_break(q) != null then
        if( preBreak != null && preBreak.size() > 0 ) {
            // <<Transplant the pre-break list 885>>;
            transplantPreBreakList( preBreak, line );
        }
        // link(q) <-- r;
        // disc_break <-- true;
        discBreak = true;

        // end
    }

    /**
     * 825.
     * <p>
     * Glue nodes in a horizontal list that is being paragraphed are not
     * supposed to include "infinite" shrinkability; that is why the algorithm
     * maintains four registers for stretching but only one for shrinking. If
     * the user tries to introduce infinite shrinkability, the shrinkability
     * will be reset to finite and an error message will be issued. A boolean
     * variable no_shrink_error_yet prevents this error message from appearing
     * more than once per paragraph.
     * <p>
     * &lt;&lt;Global variables 13>> +::=
     * <p>
     * no_shrink_error_yet: boolean; {have we complained about infinite
     * shrinkage?}
     * <p>
     * define check_shrinkage(#) ::=
     *
     * @param glue the glue to be checked
     * @return the glue value to be used instead
     */
    private FixedGlue checkShrinkage( FixedGlue glue ) {

        // if (shrink_order(#) != normal) && (shrink(#) != 0) then
        FixedGlueComponent shrink = glue.getShrink();
        if( shrink.getOrder() != FixedGlue.NORMAL_ORDER
            && shrink.getValue() != 0 ) {
            // begin # <-- finite_shrink(#);
            /*
             * 826.
             *
             * See also sections 829, 877, 895, and 942. This code is used in
             * section 815.
             *
             * function finite_shrink(p:pointer): pointer; {recovers from
             * infinite shrinkage}
             */
            // var q: pointer; {new glue specification}
            // begin if no_shrink_error_yet then
            if( noShrinkErrorYet ) {

                // begin no_shrink_error_yet <-- false;
                noShrinkErrorYet = false;
                // print_err("Infinite glue shrinkage found in a paragraph");
                // help5("The paragraph just ended includes some glue that has")

                // ("infinite shrinkability, e.g., `\hskip 0pt minus 1fil'.")
                // ("Such glue doesn't belong there---it allows a paragraph")
                // ("of any length to fit on one line. But it's safe to
                // proceed,")
                // ("since the offensive shrinkability has been made finite.");
                // error;

                logger.warning( localizer.format( "TTP.InfShringInPar" ) );
                // end ;
            }

            // q <-- new_spec(p);
            // shrink_order(q) <-- normal;
            // delete_glue_ref(p);
            // finite_shrink <-- q;
            return new Glue( glue.getLength(), glue.getStretch(),
                             GlueComponent.ZERO );
            // end ;
            // - - -

            // end
        }
        return glue;
    }

    /**
     * 865.
     * <p>
     * This code is used in sections 815 and 863.
     * </p>
     */
    private void cleanUpTheMemory() {

        active.clear();
        passive.clear();
        curBreak = 0;
    }

    /**
     * 837.
     * <p>
     * When we insert a new active node for a break at cur_p, suppose this new
     * node is to be placed just before active node a; then we essentially want
     * to insert `&delta;cur_p&delta;'' before a, where
     * &delta;=&alpha;(a)-&alpha;(cur_p) and &delta;' =
     * &alpha;(cur_p)-&alpha;(a) in the notation explained above. The
     * cur_active_width array now holds &gamma;+&beta;(cur_p)-&alpha;(a); so
     * &delta; can be obtained by subtracting cur_active_width from the quantity
     * &gamma;+&beta;(cur_p)- &alpha;(cur_p). The latter quantity can be
     * regarded as the length of a line "from cur_p to cur_p"; we call it the
     * break_width at cur_p.
     * <p>
     * The break_width is usually negative, since it consists of the background
     * (which is normally zero) minus the width of nodes following cur_p that
     * are eliminated after a break. If, for example, node cur_p is a glue node,
     * the width of this glue is subtracted from the background; and we also
     * look ahead to eliminate all subsequent glue and penalty and kern and math
     * nodes, subtracting their widths as well.
     * <p>
     * Kern nodes do not disappear at a line break unless they are explicit.
     * <p>
     * define set_break_width_to_background(#) ::= break_width[#] <--
     * background[#]
     * <p>
     * <p>
     * This code is used in section 836.
     * <p>
     * &lt;&lt;Compute the values of break_width 837>> ::=
     *
     * @param nodes the node list for the paragraph to break
     */
    private void computeBreakWidth( NodeList nodes ) {

        // begin no_break_yet <-- false;
        noBreakYet = false;
        // do_all_six(set_break_width_to_background);
        breakWidth.set( background );
        // s <-- cur_p;
        int s = curBreak;
        // if break_type > unhyphenated then
        if( breakType ) {
            // if cur_p != null then
            if( curBreak < nodes.size() ) {
                // <<Compute the discretionary break_width values 840>>;
                computeDiscretionaryBreakWidth( nodes, nodes.get( curBreak ) );
            }
        }
        // while s != null do
        for( ; s < nodes.size(); s++ ) {
            Node n = nodes.get( s );
            // begin if is_char_node(s) then
            if( n instanceof CharNode ) {
                // goto done;
                return;
                // case type(s) of
            }
            else if( n instanceof AbstractExpandableNode ) {
                // glue_node: <<Subtract glue from break_width 838>>;

                /*
                 * 838.
                 *
                 * This code is used in section 837.
                 *
                 * &lt;&lt;Subtract glue from break_width 838>> ::=
                 */
                // begin v <-- glue_ptr(s);
                // break_width[1] <-- break_width[1]-width(v);
                // break_width[2+stretch_order(v)] <--
                // break_width[2+stretch_order(v)]-stretch(v);
                // break_width[6] <-- break_width[6]-shrink(v);
                breakWidth.subtract( ((GlueNode) n).getSize() );
                // end

            }
            else if( n instanceof PenaltyNode ) {
                // penalty_node: do_nothing;
            }
            else if( n instanceof BeforeMathNode
                || n instanceof AfterMathNode ) {
                // math_node: break_width[1] <-- break_width[1]-width(s);
                breakWidth.subtract( n.getWidth() );
            }
            else if( n instanceof KernNode ) {
                // kern_node: if subtype(s) != explicit then
                if( !(n instanceof ExplicitKernNode) ) {
                    // goto done
                    return;
                }
                // else break_width[1] <-- break_width[1]-width(s);
                breakWidth.subtract( n.getWidth() );
            }
            else {
                // othercases goto done
                return;
            }
            // endcases ;
            // s <-- link(s);
            // end ;
        }
        // done: end
    }

    // <<Update the value of printed_node for symbolic displays 858>> ::=
    // if cur_p=printed_node then
    // if cur_p != null then
    // if type(cur_p)=disc_node then
    // begin t <-- replace_count(cur_p);
    // while t > 0 do
    // begin decr(t);
    // printed_node <-- link(printed_node);
    // end ;
    // end

    /**
     * 859.
     * <p>
     * This code is used in section 855.
     * <p>
     * &lt;&lt;Compute the demerits, d, from r to cur_p 859>> ::=
     *
     * @param activeNode the active node
     * @param penalty    the panalty
     * @param badness    the badness
     * @return the demerits value
     */
    private long computeDemertis( ActiveNode activeNode, long penalty,
                                  long badness ) {

        // begin d <-- line_penalty+b;
        long d = linePenalty + badness;
        // if abs(d) >= 10000 then
        if( d < -Badness.INF_PENALTY || d > Badness.INF_PENALTY ) {
            // d <-- 100000000 else d <-- d*d;
            d = Badness.INF_PENALTY * Badness.INF_PENALTY;
        }
        else {
            d *= d;
        }
        // if pi != 0 then
        if( penalty != 0 ) {
            // if pi > 0 then
            if( penalty > 0 ) {
                // d <-- d+pi*pi
                d += penalty * penalty;
                // else if pi > eject_penalty then
            }
            else if( penalty > Badness.EJECT_PENALTY ) {
                // d <-- d-pi*pi;
                d -= penalty * penalty;
            }
        }
        // if (break_type=hyphenated) && (type(r)=hyphenated)
        if( breakType && activeNode.isHyphenated() ) {
            // then
            // if cur_p != null then
            if( curBreak < active.size() ) {
                // d <-- d+double_hyphen_demerits
                d += doubleHyphenDemerits;
                // else d <-- d+final_hyphen_demerits;
            }
            else {
                d += finalHyphenDemerits;
            }
            // if abs(fit_class-fitness(r)) > 1 then
            if( !fitClass.adjacent( activeNode.getFitness() ) ) {
                // d <-- d+adj_demerits;
            }
        }
        return d;
        // end
    }

    /**
     * 840.
     * <p>
     * This code is used in section 837.
     * <p>
     * &lt;&lt;Compute the discretionary break_width values 840>> ::=
     *
     * @param nodes the node list for the paragraph to break
     * @param node  the current node
     */
    void computeDiscretionaryBreakWidth( NodeList nodes, Node node ) {

        DiscretionaryNode x = (DiscretionaryNode) node;
        // begin t <-- replace_count(cur_p);
        // v <-- cur_p;
        // s <-- post_break(cur_p);
        NodeList postBreak = x.getPostBreak();
        // while t > 0 do
        // begin decr(t);
        // v <-- link(v);
        // <<Subtract the width of node v from break_width 841>>;
        breakWidth.subtract( x.getWidth() );
        // end ;
        // while s != null do
        // begin <<Add the width of node s to break_width 842>>;
        // s <-- link(s);
        // end ;
        // break_width[1] <-- break_width[1]+disc_width;
        breakWidth.add( discretionaryWidth );
        // if post_break(cur_p)=null then
        if( postBreak != null ) {
            breakWidth.add( postBreak.getWidth() );
            // s <-- link(v); {nodes may be discardable after the break}
            // end
        }
    }

    // <<Set line length parameters in preparation for hanging
    // indentation 849>> ::=
    // begin last_special_line <-- abs(hang_after);
    // if hang_after < 0 then
    // begin first_width <-- hsize-abs(hang_indent);
    // if hang_indent >= 0 then
    // first_indent <-- hang_indent
    // else first_indent <-- 0;
    // second_width <-- hsize;
    // second_indent <-- 0;
    // end
    // else begin first_width <-- hsize;
    // first_indent <-- 0;
    // second_width <-- hsize-abs(hang_indent);
    // if hang_indent >= 0 then
    // second_indent <-- hang_indent
    // else second_indent <-- 0;
    // end ;
    // end

    /**
     * 850.
     * <p>
     * When we come to the following code, we have just encountered the first
     * active node r whose line_number field contains l. Thus we want to compute
     * the length of the lth line of the current paragraph. Furthermore, we want
     * to set old_l to the last number in the class of line numbers equivalent
     * to l.
     * <p>
     * This code is used in section 835.
     * <p>
     * &lt;&lt;Compute the new line width 850>> ::=
     *
     * @param line the line number
     */
    private void computeLineWidth( long line ) {

        // if l > easy_line then
        // begin line_width <-- second_width;
        // old_l <-- max_halfword-1;
        // end
        // else begin old_l <-- l;
        // if l > last_special_line then
        // line_width <-- second_width
        // else if par_shape_ptr=null then
        // line_width <-- first_width
        // else line_width <-- mem[par_shape_ptr+2*l].sc;
        // end
        oldL = line;
        lineWidth.set( parshape.getLength( (int) line ) );
    }

    /**
     * 864.
     * <p>
     * The active node that represents the starting point does not need a
     * corresponding passive node.
     * <p>
     * define store_background(#) ::= active_width[#] <-- background[#]
     * <p>
     * This code is used in section 863.
     */
    private void createAnActiveBreakpointRepresentingTheBeginningOfTheParagraph() {

        // q <-- get_node(active_node_size);
        // type(q) <-- unhyphenated;
        // fitness(q) <-- decent_fit;
        // link(q) <-- last_active;
        // break_node(q) <-- null;
        // line_number(q) <-- prev_graf+1;
        // total_demerits(q) <-- 0;
        // link(active) <-- q;
        active.clear();
        active
            .add( new ActiveNode( Fitness.DECENT,
                                  false,
                                  0,
                                  prevGraf + 1,
                                  null ) );
        // do_all_six(store_background);
        activeWidth.set( background );

        // passive <-- null;
        passive.clear();
        // printed_node <-- temp_head;
        printedNode = 0;
        // pass_number <-- 0;
        // font_in_short_display <-- null_font
    }

    /**
     * 836.
     * <p>
     * It is not necessary to create new active nodes having minimal_demerits
     * greater than minimum_demerits+abs(adj_demerits), since such active nodes
     * will never be chosen in the final paragraph breaks. This observation
     * allows us to omit a substantial number of feasible breakpoints from
     * further consideration.
     * <p>
     * <p>
     * This code is used in section 835.
     * <p>
     * &lt;&lt;Create new active nodes for the best feasible breaks just
     * found 836>>
     * ::=
     *
     * @param nodes the node list for the paragraph to break
     */
    private void createNewActiveNodes( NodeList nodes ) {

        // begin if no_break_yet then
        if( noBreakYet ) {
            // <<Compute the values of break_width 837>>;
            computeBreakWidth( nodes );
        }
        // <<Insert a delta node to prepare for breaks at cur_p 843>>;
        insertDeltaNodeForBreaks();
        // if abs(adj_demerits) >= awful_bad-minimum_demerits
        // then
        if( (adjDemerits < 0 ? -adjDemerits : adjDemerits) >= AWFUL_BAD
            - minimumDemerits ) {
            // minimum_demerits <-- awful_bad-1
            minimumDemerits = AWFUL_BAD - 1;
        }
        else {
            // else minimum_demerits <-- minimum_demerits+abs(adj_demerits);
            minimumDemerits += (adjDemerits < 0 ? -adjDemerits : adjDemerits);
        }
        // for fit_class <-- very_loose_fit to tight_fit do
        // begin if minimal_demerits[fit_class] =< minimum_demerits then
        // <<Insert a new active node from best_place[fit_class] to cur_p 845>>;
        // minimal_demerits[fit_class] <-- awful_bad;
        // end ;
        if( minimalDemerits[ 0 ] <= minimumDemerits ) {
            insertActiveNode( nodes, Fitness.VERY_LOOSE );
            minimalDemerits[ 0 ] = AWFUL_BAD;
        }
        if( minimalDemerits[ 1 ] <= minimumDemerits ) {
            insertActiveNode( nodes, Fitness.LOOSE );
            minimalDemerits[ 1 ] = AWFUL_BAD;
        }
        if( minimalDemerits[ 2 ] <= minimumDemerits ) {
            insertActiveNode( nodes, Fitness.DECENT );
            minimalDemerits[ 2 ] = AWFUL_BAD;
        }
        if( minimalDemerits[ 3 ] <= minimumDemerits ) {
            insertActiveNode( nodes, Fitness.TIGHT );
            minimalDemerits[ 3 ] = AWFUL_BAD;
        }

        // minimum_demerits <-- awful_bad;
        minimumDemerits = AWFUL_BAD;
        // <<Insert a delta node to prepare for the next active node 844>>;
        insertDeltaNodeForActive();
        // end
    }

    /**
     * 858.
     *
     * When the data for a discretionary break is being displayed, we will have
     * printed the pre_break and post_break lists; we want to skip over the
     * third list, so that the discretionary data will not appear twice. The
     * following code is performed at the very end of try_break.
     *
     * This code is used in section 829.
     */

    /**
     * 860.
     * <p>
     * When an active node disappears, we must delete an adjacent delta node if
     * the active node was at the beginning or the end of the active list, or if
     * it was surrounded by delta nodes. We also must preserve the property that
     * cur_active_width represents the length of material from link(prev_r) to
     * cur_p.
     * <p>
     * define combine_two_deltas(#) ::= mem[prev_r+#].sc <--
     * mem[prev_r+#].sc+mem[r+#].sc
     * <p>
     * define downdate_width(#) ::= cur_active_width[#] <--
     * cur_active_width[#]-mem[prev_r+#].sc
     * <p>
     * This code is used in section 851.
     * <p>
     * &lt;&lt;Deactivate node r 860>> ::=
     */
    private void deactivateNodeR() {

        // link(prev_r) <-- link(r);
        // free_node(r,active_node_size);
        // S ystem.err.println("deactivate " + r + "/" + active.size());
        active.remove( r );
        // if prev_r=active then
        if( prevR < 0 ) {
            // <<Update the active widths, since the first active node has been
            // deleted 861>>
            sub861();
            // else if type(prev_r)=delta_node then
        }
        else if( active.get( prevR ) instanceof DeltaNode ) {
            DeltaNode deltaPrev = (DeltaNode) active.get( prevR );
            // begin r <-- link(prev_r);
            // if r=last_active then
            if( r >= active.size() ) {
                // begin do_all_six(downdate_width);
                curActiveWidth.subtract( deltaPrev );
                // link(prev_prev_r) <-- last_active;
                active.remove( prevR );
                // free_node(prev_r,delta_node_size);
                // prev_r <-- prev_prev_r;
                prevR = prevPrevR;
                r--;
                // end
                // else if type(r)=delta_node then
            }
            else if( active.get( r ) instanceof DeltaNode ) {
                // begin do_all_six(update_width);
                DeltaNode delta = (DeltaNode) active.get( r );
                curActiveWidth.add( delta );
                // do_all_six(combine_two_deltas);
                deltaPrev.add( delta );
                // link(prev_r) <-- link(r);
                active.remove( r );
                // free_node(r,delta_node_size);
                // end ;
            }
            // end
        }
    }

    @Override
    public void enableLogging( Logger theLogger ) {

        this.logger = theLogger;
    }

    /**
     * 878.
     * <p>
     * The job of reversing links in a list is conveniently regarded as the job
     * of taking items off one stack and putting them on another. In this case
     * we take them off a stack pointed to by q and having prev_break fields; we
     * put them on a stack pointed to by cur_p and having next_break fields.
     * Node r is the passive node being moved from stack to stack.
     * <p>
     * This code is used in section 877.
     * <p>
     * &lt;&lt;Reverse the links of the relevant passive nodes, setting cur_p
      * to the
     * first breakpoint 878>> ::=
     *
     * @return the first passive node
     */
    private PassiveNode extractBestBreaks() {

        PassiveNode ptr;
        PassiveNode ret;
        // q <-- break_node(best_bet);
        PassiveNode breakNode = bestBet.getBreakNode();
        // cur_p <-- null;
        ret = null;
        // repeat r <-- q;
        do {
            ptr = breakNode;
            // q <-- prev_break(q);
            breakNode = breakNode.getPrevBreak();
            // next_break(r) <-- cur_p;
            ptr.setNextBreak( ret );
            // cur_p <-- r;
            ret = ptr;
            // until q=null
        } while( breakNode != null );

        return ret;
    }

    /**
     * 862. Breaking paragraphs into lines, continued.
     *
     * So far we have gotten a little way into the line_break routine, having
     * covered its important try_break subroutine. Now let's consider the rest
     * of the process.
     *
     * The main loop of line_break traverses the given hlist, starting at
     * link(temp_head), and calls try_break at each legal breakpoint. A variable
     * called auto_breaking is set to true except within math formulas, since
     * glue nodes are not legal breakpoints when they appear in formulas.
     *
     * The current node of interest in the hlist is pointed to by cur_p. Another
     * variable, prev_p, is usually one step behind cur_p, but the real meaning
     * of prev_p is this: If type(cur_p)=glue_node then cur_p is a legal
     * breakpoint if and only if auto_breaking is true and prev_p does not point
     * to a glue node, penalty node, explicit kern node, or math node.
     *
     * The following declarations provide for a few other local variables that
     * are used in special calculations.
     *
     * See also section 893. This code is used in section 815.
     */

    /**
     * Link the nodes from the input list to the output list.
     *
     * @param nodes    the node list for the paragraph to break
     * @param idx      the start index of the node to be linked in
     * @param to       the index of the first node beyond the ones to link in
     * @param line     the target list
     * @param lineGlue the sum of the glues encountered so far.
     * @param vlist    the vlist to add adjusted material to
     */
    private void fillLine( NodeList nodes, int idx, int to, NodeList line,
                           WideGlue lineGlue, NodeList vlist ) {

        Node n;
        int i = idx;

        if( postDiscBreak != null ) {

            int size = postDiscBreak.size();
            for( int j = 0; j < size; j++ ) {
                n = postDiscBreak.get( j );
                if( n instanceof MarkNode || n instanceof InsertionNode
                    || n instanceof AdjustNode ) {
                    vlist.add( n );
                }
                else {
                    line.add( n );
                    n.addWidthTo( lineGlue );
                }
            }
            postDiscBreak = null;
            i++;
        }
        else {
            while( i < to ) {
                n = nodes.get( i );
                if( n instanceof GlueNode ) {
                    i++;
                }
                else if( n instanceof MarkNode || n instanceof InsertionNode
                    || n instanceof AdjustNode ) {
                    vlist.add( n );
                }
                else {
                    break;
                }
            }
        }

        lineGlue.set( Dimen.ZERO_PT );

        for( ; i < to; i++ ) {
            n = nodes.get( i );
            if( n instanceof MarkNode || n instanceof InsertionNode
                || n instanceof AdjustNode ) {
                vlist.add( n );
            }
            else {
                line.add( n );
                n.addWidthTo( lineGlue );
            }
        }
    }

    /**
     * 874.
     * <p>
     * This code is used in section 873.
     */
    private void findAnActiveNodeWithFewestDemerits() {

        ActiveNode node;
        // r <-- link(active);
        // fewest_demerits <-- awful_bad;
        fewestDemerits = AWFUL_BAD;

        for( final Object o : active ) {
            // repeat if type(r) != delta_node then
            if( o instanceof ActiveNode ) {
                node = (ActiveNode) o;
                // if total_demerits(r) < fewest_demerits then
                if( node.getTotalDemerits() < fewestDemerits ) {
                    // begin fewest_demerits <-- total_demerits(r);
                    fewestDemerits = node.getTotalDemerits();
                    // best_bet <-- r;
                    bestBet = node;
                    // end ;
                }
            }
            // r <-- link(r);
            // until r=last_active;
        }

        // best_line <-- line_number(best_bet)
        bestLine = bestBet.getLineNumber();
    }

    /*
     * used when calculating character widths
     */
    // private int f; //f: internal_font_number;

    /**
     * 863.
     * <p>
     * The `loop' in the following code is performed at most thrice per call of
     * line_break, since it is actually a pass over the entire paragraph.
     * <p>
     * This code is used in section 815.
     *
     * @param nodes the node list for the paragraph to break
     * @throws GeneralException in case of an error
     */
    private void findOptimalBreakpoints( NodeList nodes )
        throws GeneralException {

        // threshold <-- pretolerance;
        threshold = preTolerance;

        // if threshold >= 0 then
        if( threshold >= 0 ) {
            // begin stat if tracing_paragraphs > 0 then
            if( tracingParagraphs ) {
                // begin begin_diagnostic;
                // print_nl("@firstpass"); end ; tats
                logger.info( "@firstpass\n" );
            }

            // second_pass <-- false;
            secondPass = false;
            // final_pass <-- false;
            finalPass = false;
            // end
            // else begin threshold <-- tolerance;
        }
        else {
            threshold = tolerance;
            // second_pass <-- true;
            secondPass = true;
            // final_pass <-- (emergency_stretch =< 0);
            finalPass = (emergencyStretch.getValue() <= 0);

            // TODO: implement tracing paragraphs
            // stat if tracing_paragraphs > 0 then
            // begin_diagnostic;
            // tats
            // end ;
        }

        // loop begin
        for( ; ; ) {
            // if threshold > inf_bad then
            if( threshold > Badness.INF_BAD ) {
                // threshold <-- inf_bad;
                threshold = Badness.INF_BAD;
            }

            // if second_pass then
            if( secondPass ) {
                // <<Initialize for hyphenating a paragraph 891>>;
                initializeForHyphenatingAParagraph();
            }

            // <<Create an active breakpoint representing the beginning of the
            // paragraph
            // 864>>;
            createAnActiveBreakpointRepresentingTheBeginningOfTheParagraph();

            // cur_p <-- link(temp_head);
            curBreak = 0;
            // auto_breaking <-- true;
            autoBreaking = true;
            // prev_p <-- cur_p; {glue at beginning is not a legal breakpoint}
            prevP = curBreak;

            int nodesSize = nodes.size();
            // while (cur_p != null) && (link(active) != last_active) do
            while( curBreak < nodesSize && active.size() > 0 ) {
                // <<Call try_break if cur_p is a legal breakpoint; on the
                // second
                // pass, also try to hyphenate the next word, if cur_p is a glue
                // node; then
                // advance cur_p to the next node of the paragraph that could
                // possibly be a
                // legal breakpoint 866>>;
                callTryBreak( nodes );
            }

            // if cur_p=null then
            if( curBreak >= nodesSize ) {
                // <<Try the final line break at the end of the paragraph, and
                // goto
                // done if the desired breakpoints have been found 873>>;
                if( tryTheFinalLineBreak( nodes ) ) {

                    // done: stat if tracing_paragraphs > 0 then
                    if( tracingParagraphs ) {
                        // begin end_diagnostic(true);
                        logger.info( "\n" );
                        // normalize_selector;
                        // end ;
                    }
                    // tats
                    return;
                }
            }
            // <<Clean up the memory by removing the break nodes 865>>;
            cleanUpTheMemory();

            // if not second_pass then
            if( !secondPass ) {
                // begin stat if tracing_paragraphs > 0 then
                if( tracingParagraphs ) {
                    // print_nl("@secondpass"); tats
                    logger.info( "@secondpass\n" );
                }

                // threshold <-- tolerance;
                threshold = tolerance;
                // second_pass <-- true;
                secondPass = true;
                // final_pass <-- (emergency_stretch =< 0);
                finalPass = (emergencyStretch.getValue() <= 0);

                // end {if at first you don't succeed, . . .}
            }
            else {
                // else begin stat if tracing_paragraphs > 0 then
                if( tracingParagraphs ) {
                    // print_nl("@emergencypass"); tats
                    logger.info( "@emergencypass\n" );
                }
                // background[2] <-- background[2]+emergency_stretch;
                background.addStretch( emergencyStretch );
                // final_pass <-- true;
                finalPass = true;
                // end ;
            }
            // end ;
        }
        // done: stat if tracing_paragraphs > 0 then
        // begin end_diagnostic(true);
        // normalize_selector;
        // end ;
        // tats
    }

    /**
     * 875.
     * <p>
     * The adjustment for a desired looseness is a slightly more complicated
     * version of the loop just considered. Note that if a paragraph is broken
     * into segments by displayed equations, each segment will be subject to the
     * looseness calculation, independently of the other segments.
     * <p>
     * This code is used in section 873.
     */
    private void findTheBestActiveNodeForTheDesiredLooseness() {

        // the difference between the current line number and the optimum
        // best_line
        long lineDiff;

        // begin r <-- link(active);
        int idx = 0;
        // actual_looseness <-- 0;
        actualLooseness = 0;

        do {
            // repeat if type(r) != delta_node then
            Object node = active.get( idx );
            if( node instanceof ActiveNode ) {
                ActiveNode aNode = (ActiveNode) node;
                // begin line_diff <-- line_number(r)-best_line;
                lineDiff = aNode.getLineNumber() - bestLine;

                // if ((line_diff < actual_looseness) && (looseness =<
                // line_diff)) ||
                // ((line_diff > actual_looseness) && (looseness >= line_diff))
                // then
                if( (lineDiff < actualLooseness && looseness <= lineDiff)
                    || (lineDiff > actualLooseness && looseness >= lineDiff) ) {
                    // begin best_bet <-- r;
                    bestBet = aNode;
                    // actual_looseness <-- line_diff;
                    actualLooseness = lineDiff;
                    // fewest_demerits <-- total_demerits(r);
                    fewestDemerits = aNode.getTotalDemerits();

                    // end
                    // else if (line_diff=actual_looseness) &&
                    // (total_demerits(r) < fewest_demerits) then
                }
                else if( lineDiff == actualLooseness
                    && aNode.getTotalDemerits() < fewestDemerits ) {
                    // begin best_bet <-- r;
                    bestBet = aNode;
                    // fewest_demerits <-- total_demerits(r);
                    fewestDemerits = aNode.getTotalDemerits();
                    // end ;
                }
                // end ;
            }

            // r <-- link(r);
            idx++;
            // until r=last_active;
        } while( idx < active.size() );

        // best_line <-- line_number(best_bet);
        bestLine = bestBet.getLineNumber();

        // end
    }

    /**
     * <p>
     * 816.
     * </p>
     * <p>
     * The first task is to move the list from head to temp_head and go into the
     * enclosing semantic level. We also append the \parfillskip glue to the end
     * of the paragraph, removing a space (or other glue node) if it was there,
     * since spaces usually precede blank lines and instances of `$$'. The
     * par_fill_skip is preceded by an infinite penalty, so it will never be
     * considered as a potential breakpoint.
     * </p>
     * <p>
     * This code assumes that a glue_node and a penalty_node occupy the same
     * number of mem words.
     * </p>
     * <p>
     * See also sections 827, 834, and 848. This code is used in section 815.
     * </p>
     *
     * @param nodes the node list for the paragraph to break
     */
    private void getReadyToStartLineBreaking( NodeList nodes ) {

        // link(temp_head) <-- link(head);
        Node n = nodes.get( nodes.size() - 1 );

        // if is_char_node(tail) then
        // tail_append(new_penalty(inf_penalty))
        // else if type(tail) != glue_node then
        // tail_append(new_penalty(inf_penalty))
        // else begin type(tail) <-- penalty_node;
        if( n instanceof GlueNode ) {
            // delete_glue_ref(glue_ptr(tail));
            // flush_node_list(leader_ptr(tail));
            nodes.remove( nodes.size() - 1 );
            // penalty(tail) <-- inf_penalty;
            // end ;
        }
        nodes.add( new PenaltyNode( Badness.INF_PENALTY ) );

        // link(tail) <-- new_param_glue(par_fill_skip_code);
        nodes.add( new GlueNode( parfillSkip, true ) );

        // init_cur_lang <-- prev_graf mod '200000;
        // init_l_hyf <-- prev_graf div '20000000;
        // init_r_hyf <-- (prev_graf div '200000) mod '100;
        // pop_nest;

        /*
         * 827.
         */

        // no_shrink_error_yet <-- true;
        noShrinkErrorYet = true;

        // check_shrinkage(left_skip);
        leftSkip = checkShrinkage( leftSkip );
        // check_shrinkage(right_skip);
        rightSkip = checkShrinkage( rightSkip );

        // q <-- left_skip;
        // r <-- right_skip;
        // background[1] <-- width(q)+width(r);
        // background[2] <-- 0;
        // background[3] <-- 0;
        // background[4] <-- 0;
        // background[5] <-- 0;
        // background[2+stretch_order(q)] <-- stretch(q);
        // background[2+stretch_order(r)] <--
        // background[2+stretch_order(r)]+stretch(r);
        // background[6] <-- shrink(q)+shrink(r);
        background.set( leftSkip );
        background.add( rightSkip );

        /**
         * 834.
         */

        // minimum_demerits <-- awful_bad;
        minimumDemerits = AWFUL_BAD;

        // minimal_demerits[tight_fit] <-- awful_bad;
        // minimal_demerits[decent_fit] <-- awful_bad;
        // minimal_demerits[loose_fit] <-- awful_bad;
        // minimal_demerits[very_loose_fit] <-- awful_bad;
        minimalDemerits[ 0 ] = AWFUL_BAD;
        minimalDemerits[ 1 ] = AWFUL_BAD;
        minimalDemerits[ 2 ] = AWFUL_BAD;
        minimalDemerits[ 3 ] = AWFUL_BAD;

        /*
         * 848.
         *
         * We compute the values of easy_line and the other local variables
         * relating to line length when the line_break procedure is initializing
         * itself.
         */
        // <<Get ready to start line breaking 816>> +::=
        // if par_shape_ptr=null then
        // if hang_indent=0 then
        // begin last_special_line <-- 0;
        // second_width <-- hsize;
        // second_indent <-- 0;
        // end
        // else <<Set line length parameters in preparation for hanging
        // indentation 849>>
        // else begin last_special_line <-- info(par_shape_ptr)-1;
        // second_width <-- mem[par_shape_ptr+2*(last_special_line+1)].sc;
        // second_indent <-- mem[par_shape_ptr+2*last_special_line+1].sc;
        // end ;
        // if looseness=0 then
        if( looseness == 0 ) {
            // easy_line <-- last_special_line
            easyLine = parshape.getSize();
        }
        else {
            // else easy_line <-- max_halfword
            easyLine = Integer.MAX_VALUE;
        }
        computeLineWidth( 0 );
    }

    /**
     * Hyphenate the following word.
     *
     * @param list  the node list to insert the hyphenation points into
     * @param start the starting index
     * @throws HyphenationException in case of an error
     */
    private void hyphenateFollowingWord( NodeList list, int start )
        throws HyphenationException {

        Node n;

        for( int i = start; i < list.size(); i++ ) {
            n = list.get( i );
            if( n instanceof CharNode ) {
                TypesettingContext tc = ((CharNode) n).getTypesettingContext();
                Language language = tc.getLanguage();
                UnicodeChar hyphen = tc.getFont().getHyphenChar();
                if( hyphen != null ) {
                    language.hyphenate( list, options, hyphen, start, false,
                                        nodeFactory );
                }
                return;
            }
        }
    }

    /**
     * &lt;&lt;Initialize for hyphenating a paragraph 891>>;
     */
    private void initializeForHyphenatingAParagraph() {

        // nothing to do
    }

    /**
     * 845.
     * <p>
     * When we create an active node, we also create the corresponding passive
     * node.
     * <p>
     * This code is used in section 836.
     * <p>
     * &lt;&lt;Insert a new active node from best_place[fit_class] to cur_p
     * 845>> ::=
     *
     * @param nodes   the node list for the paragraph to break
     * @param fitness the fitness
     */
    private void insertActiveNode( NodeList nodes, Fitness fitness ) {

        // begin q <-- get_node(passive_node_size);
        // link(q) <-- passive;
        // passive <-- q;
        // cur_break(q) <-- cur_p;
        // stat incr(pass_number);
        // serial(q) <-- pass_number; tats
        // prev_break(q) <-- best_place[fit_class];
        PassiveNode pn =
            new PassiveNode( curBreak, passive.size() + 1,
                             bestPlace[ fitClass.getOrder() ] );
        passive.add( pn );

        // q <-- get_node(active_node_size);
        // break_node(q) <-- passive;
        // line_number(q) <-- best_pl_line[fit_class]+1;
        // fitness(q) <-- fit_class;
        // type(q) <-- break_type;
        // total_demerits(q) <-- minimal_demerits[fit_class];
        ActiveNode an =
            new ActiveNode( fitClass, breakType,
                            minimalDemerits[ fitClass.getOrder() ],
                            bestPlaceLine[ fitClass.getOrder() ] + 1, pn );
        // link(q) <-- r;
        // link(prev_r) <-- q;
        // prev_r <-- q;
        active.add( r, an );
        prevR++;
        r++;

        // stat if tracing_paragraphs > 0 then
        if( tracingParagraphs ) {
            // <<Print a symbolic description of the new break node 846>>;
            printNewBreakNode( an );
            // tats
        }
        // end
    }

    /**
     * 844.
     * <p>
     * When the following code is performed, we will have just inserted at least
     * one active node before r, so type(prev_r) != delta_node.
     * <p>
     * define new_delta_from_break_width(#) ::= mem[q+#].sc <--
     * cur_active_width[#]-break_width[#]
     * <p>
     * This code is used in section 836.
     * <p>
     * &lt;&lt;Insert a delta node to prepare for the next active node 844>> ::=
     */
    private void insertDeltaNodeForActive() {

        // if r != last_active then
        if( r < active.size() ) {
            DeltaNode delta = new DeltaNode( curActiveWidth );
            delta.subtract( breakWidth );
            // begin q <-- get_node(delta_node_size);
            // link(q) <-- r;
            // type(q) <-- delta_node;
            // subtype(q) <-- 0; {the subtype is not used}
            // do_all_six(new_delta_from_break_width);
            // link(prev_r) <-- q;
            active.add( prevR + 1, delta );
            // prev_prev_r <-- prev_r;
            prevPrevR = prevR;
            // prev_r <-- q;
            prevR++;
            r++;
            // end
        }
    }

    // <<Add the width of node s to break_width 842>> ::=
    // if is_char_node(s) then
    // begin f <-- font(s);
    // break_width[1] <--
    // break_width[1]+char_width(f)(char_info(f)(character(s)));
    // end
    // else case type(s) of
    // ligature_node: begin f <-- font(lig_char(s));
    // break_width[1] <-- break_width[1]+char_width(f)(char_info(
    // f)(character(lig_char(s))));
    // end ;
    // hlist_node,vlist_node,rule_node,kern_node:
    // break_width[1] <-- break_width[1]+width(s);
    // othercases confusion("disc2")
    // endcases

    /**
     * 843.
     * <p>
     * We use the fact that type(active) != delta_node.
     * <p>
     * define convert_to_break_width(#) ::= mem[prev_r+#].sc <--
     * mem[prev_r+#].sc-cur_active_width[ #]+break_width[#]
     * <p>
     * define store_break_width(#) ::= active_width[#] <-- break_width[ #]
     * <p>
     * define new_delta_to_break_width(#) ::= mem[q+#].sc <--
     * break_width[#]-cur_active_width[#]
     * <p>
     * This code is used in section 836.
     * <p>
     * &lt;&lt;Insert a delta node to prepare for breaks at cur_p 843>> ::=
     */
    private void insertDeltaNodeForBreaks() {

        Object prevNode;
        // if type(prev_r)=delta_node then {modify an existing delta node}
        // else if prev_r=active then {no delta node needed at the beginning}
        if( prevR < 0 ) {
            // begin do_all_six(store_break_width);
            activeWidth.set( breakWidth );
            // end
        }
        else if( (prevNode = active.get( prevR )) instanceof DeltaNode ) {
            // begin do_all_six(convert_to_break_width);
            ((DeltaNode) prevNode).subtract( curActiveWidth );
            ((DeltaNode) prevNode).add( breakWidth );
            // end
            // else begin q <-- get_node(delta_node_size);
        }
        else {
            DeltaNode delta = new DeltaNode( breakWidth );
            delta.subtract( curActiveWidth );
            // link(q) <-- r;
            // type(q) <-- delta_node;
            // subtype(q) <-- 0; {the subtype is not used}
            // do_all_six(new_delta_to_break_width);
            // link(prev_r) <-- q;
            active.add( r, delta );
            // prev_prev_r <-- prev_r;
            prevPrevR = prevR;
            // prev_r <-- q;
            prevR++;
            r++;
            // end
        }
    }

    /**
     * 889.
     * <p>
     * Now q points to the hlist that represents the current line of the<
     * paragraph. We need to compute the appropriate line width, pack the line
     * into a box of this size, and shift the box by the appropriate amount of
     * indentation.
     * <p>
     * This code is used in section 880.
     * <p>
     * &lt;&lt;Call the packaging subroutine, setting just_box to the
     * justified box
     * 889>> ::=
     *
     * @param line    the current line
     * @param curLine the current line number
     * @param glue    the sum of the glues in the line
     */
    private void justifyLine( HorizontalListNode line, long curLine,
                              WideGlue glue ) {

        // if cur_line > last_special_line then
        // begin cur_width <-- second_width;
        // cur_indent <-- second_indent;
        // end
        // else if par_shape_ptr=null then
        // begin cur_width <-- first_width;
        // cur_indent <-- first_indent;
        // end
        // else begin cur_width <-- mem[par_shape_ptr+2*
        // cur_line].sc;
        // cur_indent <-- mem[par_shape_ptr+2*cur_line-1].sc;
        // end ;
        // adjust_tail <-- adjust_head;
        // just_box <-- hpack(q,cur_width,exactly);

        line.hpack( parshape.getLength( (int) curLine ) );
        justBox = line;

        // shift_amount(just_box) <-- cur_indent
        justBox.setShift( parshape.getIndent( (int) curLine ) );
    }

    /**
     * define kern_break ::=
     *
     * @param nodes the node list for the paragraph to break
     */
    private void kernBreak( NodeList nodes ) {

        // begin if not is_char_node(link(cur_p)) && auto_breaking then
        Node n = nodes.get( curBreak + 1 );
        if( !(n instanceof CharNode) && autoBreaking ) {
            // if type(link(cur_p))=glue_node then
            if( n instanceof AbstractExpandableNode ) {
                // try_break(0,unhyphenated);
                tryBreak( nodes, 0, false );
            }
        }
        // act_width <-- act_width+width(cur_p);
        activeWidth.add( nodes.get( curBreak ).getWidth() );
        // end
    }

    /**
     * 881.
     * <p>
     * At the end of the following code, q will point to the final node on the
     * list about to be justified.
     * <p>
     * This code is used in section 880.
     * <p>
     * &lt;&lt;Modify the end of the line to reflect the nature of the break
     * and to
     * include \rightskip; also set the proper value of disc_break 881>> ::=
     *
     * @param nodes    the node list for the paragraph to break
     * @param line     the current line
     * @param theBreak the current break point
     */
    private void modifyEndOfLine( NodeList nodes, NodeList line,
                                  int theBreak ) {

        // q <-- cur_break(cur_p);
        // disc_break <-- false;
        discBreak = false;
        // post_disc_break <-- false;
        postDiscBreak = null;
        // if q != null then {q cannot be a char_node}
        if( theBreak < nodes.size() ) {
            Node q = nodes.get( theBreak );
            // if type(q)=glue_node then
            if( q instanceof GlueNode ) {
                // begin delete_glue_ref(glue_ptr(q));
                // glue_ptr(q) <-- right_skip;
                // subtype(q) <-- right_skip_code+1;
                // add_glue_ref(right_skip);
                ((GlueNode) q).setSize( rightSkip );
                // goto done;
                return;
                // end
                // else begin if type(q)=disc_node then
            }
            else if( q instanceof DiscretionaryNode ) {
                // <<Change discretionary to compulsory and set disc_break <--
                // true 882>>
                changeDiscretionary( (DiscretionaryNode) q, line );
                // else if (type(q)=math_node) || (type(q)=kern_node) then
            }
            else if( q instanceof BeforeMathNode
                || q instanceof AfterMathNode || q instanceof KernNode ) {
                // width(q) <-- 0;
                q.setWidth( Dimen.ZERO_PT );
                // end
                // else begin q <-- temp_head;
            }
            else {
                // while link(q) != null do
                // q <-- link(q);
                // TODO put q to end???
                // end ;
            }
            // <<Put the (r)\rightskip glue after node q 886>>;

            /*
             * 886.
             *
             * This code is used in section 881.
             *
             * &lt;&lt;Put the (r)\rightskip glue after node q 886>> ::=
             */

            // r <-- new_param_glue(right_skip_code);
            // link(r) <-- link(q);
            // link(q) <-- r;
            // q <-- r
            line.add( new GlueNode( rightSkip, true ) );
            // - - -

        }
        // done:
    }

    /**
     * 877.
     * <p>
     * The total number of lines that will be set by post_line_break is
     * best_line-prev_graf-1. The last breakpoint is specified by
     * break_node(best_bet), and this passive node points to the other
     * breakpoints via the prev_break links. The finishing-up phase starts by
     * linking the relevant passive nodes in forward order, changing prev_break
     * to next_break. (The next_break fields actually reside in the same memory
     * space as the prev_break fields did, but we give them a new name because
     * of their new significance.) Then the lines are justified, one by one.
     *
     * <pre>
     * define next_break ::= prev_break {new name for prev_break
     * after links are reversed}
     *
     * procedure post_line_break(final_widow_penalty: integer);
     * </pre>
     *
     * @param nodes the node list for the paragraph to break
     * @return the post line break node list
     * @throws HelpingException in case of an error
     */
    private NodeList postLineBreak( NodeList nodes ) throws HelpingException {

        VerticalListNode vlist = new VerticalListNode();
        HorizontalListNode line;
        PassiveNode curP;

        // label done,done1;

        // var q,r,s: pointer; {temporary registers for list manipulation}

        // disc_break: boolean; {was the current break at a discretionary
        // node?}

        // post_disc_break: boolean; {and did it have a nonempty post-break
        // part?}

        // cur_width: scaled; {width of line number cur_line}

        // cur_indent: scaled; {left margin of line number cur_line}

        // t: quarterword; {used for replacement counts in discretionary nodes}

        // pen: integer; {use when calculating penalties between lines}

        // cur_line: halfword; {the current line number being justified}

        // begin <<Reverse the links of the relevant passive nodes, setting
        // cur_p to the first breakpoint 878>>;
        curP = extractBestBreaks();

        // cur_line <-- prev_graf+1;
        long curLine = prevGraf + 1;

        int idx = 0;
        WideGlue lineGlue = new WideGlue();
        NodeList adjust;

        // repeat
        do {
            line = new HorizontalListNode();
            adjust = new HorizontalListNode();
            int theBreak = curP.getCurBreak();
            fillLine( nodes, idx, theBreak, line, lineGlue, adjust );
            idx = theBreak;

            // <<Justify the line ending at breakpoint cur_p, and
            // append it to the current vertical list, together with associated
            // penalties and other insertions 880>>;
            /*
             * 880.
             *
             * The current line to be justified appears in a horizontal list
             * starting at link(temp_head) and ending at cur_break(cur_p). If
             * cur_break(cur_p) is a glue node, we reset the glue to equal the
             * right_skip glue; otherwise we append the right_skip glue at the
             * right. If cur_break(cur _p) is a discretionary node, we modify
             * the list so that the discretionary break is compulsory, and we
             * set disc_break to true. We also append the left_skip glue at the
             * left of the line, unless it is zero.
             *
             * This code is used in section 877.
             *
             * &lt;&lt;Justify the line ending at breakpoint cur_p, and
             * append it to
             * the current vertical list, together with associated penalties and
             * other insertions 880>> ::=
             */
            // <<Modify the end of the line to reflect the nature of the break
            // and to include \rightskip; also set the proper value of
            // disc_break 881>>;
            modifyEndOfLine( nodes, line, theBreak );
            // <<Put the (l)\leftskip glue at the left and detach this line
            // 887>>;
            putLeftskipAndDetach( line );
            // <<Call the packaging subroutine, setting box to the justified
            // box 889>>;
            justifyLine( line, curLine, lineGlue );
            // <<Append the new box to the current vertical list, followed by
            // the
            // list of special nodes taken out of the box by the packager 888>>;
            /*
             * 888.
             *
             * This code is used in section 880. <<Append the new box to the
             * current vertical list, followed by the list of special nodes
             * taken out of the box by the packager 888>> ::=
             */
            // <append_to_vlist(just_box);
            vlist.add( line );
            // if adjust_head != adjust_tail then
            // begin link(tail) <-- link(adjust_head);
            // tail <-- adjust_tail;
            // end ;
            // adjust_tail <-- null
            for( int i = 0; i < adjust.size(); i++ ) {
                vlist.add( adjust.get( i ) );
            }
            // - - -

            // <<Append a penalty node, if a nonzero penalty is appropriate
            // 890>>
            appendPenalty( vlist, curLine );
            // - - -

            // incr(cur_line);
            curLine++;
            // cur_p <-- next_break(cur_p);
            curP = curP.getNextBreak();
            // if cur_p != null then
            if( curP != null ) {
                // if not post_disc_break then
                if( postDiscBreak == null ) {
                    // <<Prune unwanted nodes at the beginning of the next line
                    // 879>>;
                    idx = pruneUnwantedNodes( nodes, idx, theBreak );
                }
            }

            // until cur_p=null;
        } while( curP != null );

        // if (cur_line != best_line) || (link(temp_head) != null) then
        if( curLine != bestLine ) {
            // confusion("line breaking");
            throw new HelpingException( localizer, "Panic.Line.Breaking" );
        }

        // prev_graf <-- best_line-1;
        prevGraf = bestLine - 1;

        // end ;
        return vlist;
    }

    /**
     * Initializes the field {@code parshape} if not set already. For this
     * purpose the options are considered.
     *
     * <p>The Parameter {@code \hangafter}</p>
     *
     *
     * <p>The Parameter {@code \hangindent}</p>
     *
     *
     * <p>The Parameter {@code \hsize}</p>
     * The parameter {@code \hsize} contains the horizontal size of the
     * paragraph to be build. See also {@code \parshape}, {@code \hangindent},
     * and {@code \hangafter}.
     */
    private void prepareParshape() {

        parshape = options.getParshape();

        if( parshape == null ) {
            int hangafter =
                (int) options.getCountOption( "hangafter" ).getValue();

            if( hangafter != 0 ) {
                hangingParshape.setHangafter( hangafter );
                hangingParshape.setHangindent( options
                                                   .getDimenOption(
                                                       "hangindent" ) );
                hangingParshape.setHsize( options.getDimenOption( "hsize" ) );
                parshape = hangingParshape;
            }
            else {
                fixedParshape.setHsize( options.getDimenOption( "hsize" ) );
                parshape = fixedParshape;
            }
        }
    }

    /**
     * 854.
     * <p>
     * During the final pass, we dare not lose all active nodes, lest we lose
     * touch with the line breaks already found. The code shown here makes sure
     * that such a catastrophe does not happen, by permitting overfull boxes as
     * a last resort. This particular part of TeX was a source of several
     * subtle bugs before the correct
     * program logic was finally discovered; readers who seek to "improve"
     * TeX should therefore think thrice before daring to make any
     * changes here.
     * <p>
     * This code is used in section 851.
     * <p>
     * &lt;&lt;Prepare to deactivate node r, and goto deactivate unless there
     * is a
     * reason to consider lines of text from r to cur_p 854>> ::=
     *
     * @param badness the badness
     * @return the indicator for the continuation
     */
    private boolean prepareToDeactivate( long badness ) {

        // begin if final_pass && (minimum_demerits=awful_bad) &&
        // (link(r)=last_active) && (prev_r=active) then
        // goto deactivate;
        if( finalPass && minimumDemerits == AWFUL_BAD && r + 1 >= active.size()
            && prevR < 0 ) {
            // artificial_demerits <-- true {set demerits zero, this break is
            // forced}
            artificialDemerits = true;
            // else if b > threshold then
        }
        else {
            return badness > threshold;
        }
        // node_r_stays_active <-- false;
        return false;
        // end
    }

    /**
     * 856.
     * <p>
     * This code is used in section 855.
     * <p>
     * &lt;&lt;Print a symbolic description of this feasible break 856>> ::=
     *
     * @param nodes   the node list for the paragraph to break
     * @param d       the demerits
     * @param penalty the penalty
     * @param badness the badness
     */
    private void printFeasibleBreak( NodeList nodes, long d, long penalty,
                                     int badness ) {

        StringBuilder sb = new StringBuilder();
        // begin if printed_node != cur_p then
        if( printedNode != curBreak ) {
            // <<Print the list between printed_node and cur_p, then set
            // printed_node <-- cur_p 857>>;
            printList( sb, nodes );
        }
        // print_nl("@");
        sb.append( "@" );
        // if cur_p=null then
        if( curBreak >= nodes.size() ) {
            // print_esc("par")
            sb.append( "\\par" );
        }
        else {
            Node n = nodes.get( curBreak );
            if( n instanceof AbstractExpandableNode ) {
                // else if type(cur_p) != glue_node then
                // begin if type(cur_p)=penalty_node then
            }
            else if( n instanceof PenaltyNode ) {
                // print_esc("penalty")
                sb.append( "\\penalty" );
                // else if type(cur_p)=disc_node then
            }
            else if( n instanceof DiscretionaryNode ) {
                // print_esc("discretionary")
                sb.append( "\\discretionary" );
                // else if type(cur_p)=kern_node then
            }
            else if( n instanceof KernNode ) {
                // print_esc("kern")
                sb.append( "\\kern" );
                // else print_esc("math");
            }
            else {
                sb.append( "\\math" );
            }
            // end ;
        }
        PassiveNode breakNode =
            (r < active.size() ? ((ActiveNode) active.get( r ))
                .getBreakNode() : null);
        // print(" via @@");
        sb.append( " via @@" );
        // if break_node(r)=null then
        // print_char("0")
        // else print_int(serial(break_node(r)));
        sb.append( breakNode == null ? "0" : "" + breakNode.getSerial() );
        // print(" b=");
        // if b > inf_bad then print_char("*") else print_int(b);
        sb.append( badness > Badness.INF_BAD ? " b=*" : " b=" + badness );
        // print(" p=");
        sb.append( " p=" );
        // print_int(pi);
        sb.append( penalty );
        // print(" d=");
        // if artificial_demerits then print_char("*") else print_int(d);
        sb.append( artificialDemerits ? " d=*" : " d=" + d );
        sb.append( "\n" );
        logger.info( sb.toString() );
        // end
    }

    /*
     * 876.
     *
     * Once the best sequence of breakpoints has been found (hurray), we call on
     * the procedure post_line_break to finish the remainder of the work. (By
     * introducing this subprocedure, we are able to keep line_break from
     * getting extremely long.)
     *
     * &lt;&lt;Break the paragraph at the chosen breakpoints, justify the
     * resulting
     * lines to the correct widths, and append them to the current vertical list
     * 876>> ::= post_line_break(final_widow_penalty)
     *
     * This code is used in section 815.
     */

    /**
     * 857.
     * <p>
     * This code is used in section 856.
     * <p>
     * &lt;&lt;Print the list between printed_node and cur_p, then set
     * printed_node
     * <-- cur_p 857>> ::=
     *
     * @param sb    the target string buffer
     * @param nodes the node list for the paragraph to break
     */
    private void printList( StringBuilder sb, NodeList nodes ) {

        // begin print_nl("");
        // if cur_p=null then
        // short_display(link(printed_node))
        // else begin save_link <-- link(cur_p);
        // link(cur_p) <-- null;
        // print_nl("");
        // short_display(link(printed_node));
        // link(cur_p) <-- save_link;
        // end ;
        for( int i = printedNode + 1; i < curBreak; i++ ) {
            nodes.get( i ).toText( sb, "" );
            sb.append( "\n" );
        }

        // printed_node <-- cur_p;
        printedNode = curBreak;
        // end
    }

    /**
     * 846.
     * <p>
     * This code is used in section 845.
     * <p>
     * &lt;&lt;Print a symbolic description of the new break node 846>> ::=
     *
     * @param aNode the active node
     */
    private void printNewBreakNode( ActiveNode aNode ) {

        // begin print_nl("@@");
        StringBuilder sb = new StringBuilder( "@@" );
        // print_int(serial(passive));
        PassiveNode passiveNode = passive.get( passive.size() - 1 );
        sb.append( passiveNode.getSerial() );
        // print(": line ");
        sb.append( ": line " );
        // print_int(line_number(q)-1);
        sb.append( aNode.getLineNumber() - 1 );
        // print_char(".");
        sb.append( "." );
        // print_int(fit_class);
        sb.append( fitClass.getOrder() );
        // if break_type=hyphenated then
        if( breakType ) {
            // print_char("-");
            sb.append( "-" );
        }
        // print(" t=");
        sb.append( " t=" );
        // print_int(total_demerits(q));
        sb.append( aNode.getTotalDemerits() );

        // print(" - > @@");
        sb.append( " - > @@" );
        // if prev_break(passive)=null then
        PassiveNode prevBreak = passiveNode.getPrevBreak();
        if( prevBreak == null ) {
            // print_char("0")
            sb.append( "0" );
        }
        else {
            // else print_int(serial(prev_break(passive)));
            sb.append( prevBreak.getSerial() );
        }

        sb.append( "\n" );
        logger.info( sb.toString() );
        // end
    }

    /**
     * 879.
     * <p>
     * Glue and penalty and kern and math nodes are deleted at the beginning of
     * a line, except in the anomalous case that the node to be deleted is
     * actually one of the chosen breakpoints. Otherwise the pruning done here
     * is designed to match the lookahead computation in try_break, where the
     * break_width values are computed for non-discretionary breakpoints.
     * <p>
     * This code is used in section 877.
     * <p>
     * &lt;&lt;Prune unwanted nodes at the beginning of the next line 879>> ::=
     *
     * @param nodes the node list for the paragraph to break
     * @param idx   start index
     * @param to    end index
     * @return the actual end index
     */
    private int pruneUnwantedNodes( NodeList nodes, int idx, int to ) {

        Node q;
        int i = idx;
        // begin r <-- temp_head;
        // loop begin q <-- link(r);
        while( i < to ) {
            q = nodes.get( i );
            // if q=cur_break(cur_p) then
            // goto done1; {cur_break(cur_p) is the next breakpoint}
            // {now q cannot be null}
            // if is_char_node(q) then
            if( q instanceof CharNode ) {
                // goto done1;
                break;
            }
            // if non_discardable(q) then
            if( !(q instanceof Discardable) ) {
                // goto done1;
                break;
            }
            // if type(q)=kern_node then
            if( q instanceof KernNode ) {
                // if subtype(q) != explicit then
                if( !(q instanceof ExplicitKernNode) ) {
                    // goto done1;
                    break;
                }
            }
            // r <-- q; {now type(q)=glue_node, kern_node, math_node or
            // penalty_node}
            i++;
            // end ;
        }
        // done1: if r != temp_head then
        // begin link(r) <-- null;
        // flush_node_list(link(temp_head));
        // link(temp_head) <-- q;
        // end ;

        return i;
        // end
    }

    /**
     * 887.
     * <p>
     * The following code begins with q at the end of the list to be justified.
     * It ends with q at the beginning of that list, and with link(temp_head)
     * pointing to the remainder of the paragraph, if any.
     * <p>
     * This code is used in section 880.
     * <p>
     * &lt;&lt;Put the (l)\leftskip glue at the left and detach this line
     * 887>> ::=
     *
     * @param line the current line
     */
    private void putLeftskipAndDetach( NodeList line ) {

        // r <-- link(q);
        // link(q) <-- null;
        // q <-- link(temp_head);
        // link(temp_head) <-- r;
        // if left_skip != zero_glue then
        if( leftSkip.ne( FixedGlue.ZERO ) ) {
            // begin r <-- new_param_glue(left_skip_code);
            // link(r) <-- q;
            // q <-- r;
            line.add( new GlueNode( leftSkip, true ) );
            // end
        }
    }

    /**
     * 855.
     * <p>
     * When we get to this part of the code, the line from r to cur_p is
     * feasible, its badness is b, and its fitness classification is fit_class.
     * We don't want to make an active node for this break yet, but we will
     * compute the total demerits and record them in the minimal_demerits array,
     * if such a break is the current champion among all ways to get to cur_p in
     * a given line-number class and fitness class.
     * <p>
     * This code is used in section 851.
     * <p>
     * &lt;&lt;Record a new feasible break 855>> ::=
     *
     * @param nodes   the node list for the paragraph to break
     * @param penalty the penalty
     * @param badness the badness
     */
    private void recordNewFeasibleBreak( NodeList nodes, long penalty,
                                         int badness ) {

        long d;
        // if artificial_demerits then
        if( artificialDemerits ) {
            // d <-- 0
            d = 0;
        }
        else {
            // else <<Compute the demerits, d, from r to cur_p 859>>;
            d = computeDemertis( (ActiveNode) active.get( r ),
                                 penalty,
                                 badness );
        }
        // stat if tracing_paragraphs > 0 then
        if( tracingParagraphs ) {
            // <<Print a symbolic description of this feasible break 856>>;
            printFeasibleBreak( nodes, d, penalty, badness );
        }
        // tats
        // d <-- d+total_demerits(r); {this is the minimum total demerits
        // from the beginning to cur_p via r}
        if( r < active.size() ) {
            d += ((ActiveNode) active.get( r )).getTotalDemerits();
        }
        // if d =< minimal_demerits[fit_class] then
        int fit = fitClass.getOrder();
        if( d <= minimalDemerits[ fit ] ) {
            // begin minimal_demerits[fit_class] <-- d;
            minimalDemerits[ fit ] = d;
            // best_place[fit_class] <-- break_node(r);
            bestPlace[ fit ] = ((ActiveNode) active.get( r )).getBreakNode();
            // best_pl_line[fit_class] <-- l;
            bestPlaceLine[ fit ] = l;
            // if d < minimum_demerits then
            if( d < minimumDemerits ) {
                // minimum_demerits <-- d;
                minimumDemerits = d;
            }
            // end
        }
    }

    @Override
    public void setNodefactory( NodeFactory factory ) {

        this.nodeFactory = factory;
    }

    @Override
    public void setOptions( TypesetterOptions options ) {

        this.options = options;
    }

    /**
     * <p>
     * 835.
     * </p>
     * <p>
     * The first part of the following code is part of TeX's inner loop, so
     * we don't want to waste any time. The
     * current active node, namely node r, contains the line number that will be
     * considered next. At the end of the list we have arranged the data
     * structure so that r=last_active and line_number(last_active) > old_l.
     * </p>
     * <p>
     * This code is used in section 829.
     * </p>
     * <p>
     * &lt;&lt;If a line number class has ended, create new active nodes for
     * the best
     * feasible breaks in that class; then return if r=last_active, otherwise
     * compute the new line_width 835>> ::=
     * </p>
     *
     * @param nodes the node list for the paragraph to break
     * @return the indicator that the processing is at its end
     */
    private boolean sub835( NodeList nodes ) {

        if( r >= active.size() ) {
            l = Integer.MAX_VALUE;
        }
        else {
            // begin l <-- line_number(r);
            l = ((ActiveNode) active.get( r )).getLineNumber();
        }
        // if l > old_l then
        if( l > oldL ) {
            // begin {now we are no longer in the inner loop}
            // if (minimum_demerits < awful_bad) && ((old_l != easy_line)
            // || (r=last_active)) then
            if( minimumDemerits < AWFUL_BAD
                && (oldL != easyLine || r >= active.size()) ) {
                // <<Create new active nodes for the best feasible breaks just
                // found 836>>;
                createNewActiveNodes( nodes );
            }
            // if r=last_active then
            if( r >= active.size() ) {
                // return ;
                return true;
            }
            // <<Compute the new line width 850>>;
            computeLineWidth( l );
            // end ;
        }
        // end
        return false;
    }

    /**
     * 841.
     * <p>
     * Replacement texts and discretionary texts are supposed to contain only
     * character nodes, kern nodes, ligature nodes, and box or rule nodes.
     * <p>
     * This code is used in section 840.
     * <p>
     * &lt;&lt;Subtract the width of node v from break_width 841>> ::=
     *
     * @param v ...
     */
    private void sub841( Node v ) {

        // if is_char_node(v) then
        // begin f <-- font(v);
        // break_width[1] <-- break_width[1]-char_width(f)(char_info(
        // f)(character(v)));
        // end
        // else case type(v) of
        // ligature_node: begin f <-- font(lig_char(v));
        // break_width[1] <-- break_width[1]-char_width(f)(char
        // _info(f)(character(lig_char(v))));
        // end ;
        // hlist_node,vlist_node,rule_node,kern_node:
        // break_width[1] <-- break_width[1]-width(v);
        breakWidth.subtract( v.getWidth() );
        // othercases confusion("disc1")
        // endcases
    }

    /**
     * 851.
     * <p>
     * The remaining part of try_break deals with the calculation of demerits
     * for a break from r to cur_p.
     * <p>
     * The first thing to do is calculate the badness, b. This value will always
     * be between zero and inf_bad+1; the latter value occurs only in the case
     * of lines from r to cur_p that cannot shrink enough to fit the necessary
     * width. In such cases, node r will be deactivated. We also deactivate node
     * r when a break at cur_p is forced, since future breaks must go through a
     * forced break.
     * <p>
     * This code is used in section 829.
     * <p>
     * &lt;&lt;Consider the demerits for a line from r to cur_p; deactivate
     * node r if
     * it should no longer be active; then goto continue if a line from r to
     * cur_p is infeasible, otherwise record a new feasible break 851>> ::=
     *
     * @param nodes   the node list for the paragraph to break
     * @param penalty the penalty
     * @return {@code true} iff ...
     */
    private boolean sub851( NodeList nodes, long penalty ) {

        // node_r_stays_active: boolean; {should node r remain in the
        // active list?}
        boolean nodeRStaysActive;

        // b: halfword; {badness of test line}
        int badness;

        // begin artificial_demerits <-- false;
        artificialDemerits = false;
        // shortfall <-- line_width-cur_active_width[1]; {we're this much too
        // short}
        shortfall.set( lineWidth );
        shortfall.subtract( curActiveWidth.getLength() );
        // if shortfall > 0 then
        if( shortfall.gt( Dimen.ZERO_PT ) ) {
            // <<Set the value of b to the badness for stretching the line, and
            // compute the corresponding fit_class 852>>
            badness = badnessForStretching();
        }
        else {
            // else <<Set the value of b to the badness for shrinking the line,
            // and compute the corresponding fit_class 853>>;
            badness = badnessForShrinking();
        }
        // if (b > inf_bad) || (pi=eject_penalty) then
        if( badness > Badness.INF_BAD || penalty == Badness.EJECT_PENALTY ) {
            // <<Prepare to deactivate node r, and goto deactivate unless there
            // is a reason to consider lines of text from r to cur_p 854>>
            if( prepareToDeactivate( badness ) ) {

                // deactivate: <<Deactivate node r 860>>;
                deactivateNodeR();
                return false;
            }
            else {
                nodeRStaysActive = false;
            }
        }
        else {
            // else begin prev_r <-- r;
            prevR = r;
            // if b > threshold then
            if( badness > threshold ) {
                // goto continue;
                return true;
            }
            // node_r_stays_active <-- true;
            nodeRStaysActive = true;
            // end ;
        }
        // <<Record a new feasible break 855>>;
        recordNewFeasibleBreak( nodes, penalty, badness );
        // if node_r_stays_active then
        if( nodeRStaysActive ) {
            // goto continue; {prev_r has been set to r}
            return true;
        }
        // deactivate: <<Deactivate node r 860>>;
        deactivateNodeR();
        return false;
        // end
    }

    /**
     * 861.
     * <p>
     * The following code uses the fact that type(last_active) != delta_node. If
     * the active list has just become empty, we do not need to update the
     * active_width array, since it will be initialized when an active node is
     * next inserted.
     * <p>
     * define update_active(#) ::= active_width[#] <--
     * active_width[#]+mem[r+#].sc
     * <p>
     * This code is used in section 860.
     * <p>
     * &lt;&lt;Update the active widths, since the first active node has been
     * deleted
     * 861>> ::=
     */
    private void sub861() {

        // begin r <-- link(active);
        r = 0;
        // if type(r)=delta_node then
        if( !active.isEmpty() && active.get( r ) instanceof DeltaNode ) {
            // begin do_all_six(update_active);
            activeWidth.add( (DeltaNode) active.get( r ) );
            // do_all_six(copy_to_cur_active);
            curActiveWidth.set( activeWidth );
            // link(active) <-- link(r);
            // free_node(r,delta_node_size);
            active.remove( r );
            // end ;
        }
        // end
    }

    /**
     * 883.
     * <p>
     * This code is used in section 882.
     * <p>
     * &lt;&lt;Destroy the t nodes following q, and make r point to the
     * following node
     * 883>> ::=
     */
    private void sub883() {

        // if t=0 then
        // r <-- link(q)
        // else begin r <-- q;
        // while t > 1 do
        // begin r <-- link(r);
        // decr(t);
        // end ;
        // s <-- link(r);
        // r <-- link(s);
        // link(s) <-- null;
        // flush_node_list(link(q));
        // replace_count(q) <-- 0;
        // end
    }

    /**
     * 884.
     * <p>
     * We move the post-break list from inside node q to the main list by
     * re-attaching it just before the present node r, then resetting r.
     * <p>
     * This code is used in section 882.
     * <p>
     * &lt;&lt;Transplant the post-break list 884>> ::=
     *
     * @param postBreak the list of post break items
     */
    private void transplantPostBreakList( NodeList postBreak ) {

        // begin s <-- post_break(q);
        // while link(s) != null do
        // s <-- link(s);
        // link(s) <-- r;
        // r <-- post_break(q);
        // post_break(q) <-- null;
        // post_disc_break <-- true;
        postDiscBreak = postBreak;
        // end
    }

    /**
     * 885.
     * <p>
     * We move the pre-break list from inside node q to the main list by
     * re-attaching it just after the present node q, then resetting q.
     * <p>
     * This code is used in section 882.
     * <p>
     * &lt;&lt;Transplant the pre-break list 885>> ::=
     *
     * @param preBreak the pre-break list
     * @param line     the current line
     */
    private void transplantPreBreakList( NodeList preBreak, NodeList line ) {

        // begin s <-- pre_break(q);
        // link(q) <-- s;
        // while link(s) != null do
        // s <-- link(s);
        // pre_break(q) <-- null;
        // q <-- s;
        for( int i = 0; i < preBreak.size(); i++ ) {
            line.add( preBreak.get( i ) );
        }
        // end
    }

    /**
     * procedure try_break(pi:integer;break_type: small_number);
     *
     * @param nodes      the node list for the paragraph to break
     * @param penalty    the initial penalty
     * @param hyphenated the break type
     */
    private void tryBreak( NodeList nodes, long penalty, boolean hyphenated ) {

        long pen = penalty;
        breakType = hyphenated;

        // label exit,done,done1,continue, deactivate;

        // var r: pointer; {runs through the active list}

        // <<Other local variables for try_break 830>>

        /*
         * 830.
         *
         * This code is used in section 829.
         */

        // <<Other local variables for try_break 830>> ::=
        // s: pointer; {runs through nodes ahead of cur_p}
        // q: pointer; {points to a new node being created}
        // v: pointer; {points to a glue specification or a node ahead of
        // cur_p}
        // t: integer; {node count, if cur_p is a discretionary node}
        // d: integer; {demerits of test line}
        // save_link: pointer; {temporarily holds value of link(cur_p)}
        // begin <<Make sure that pi is in the proper range 831>>;
        /*
         * 831.
         *
         * This code is used in section 829.
         */
        // <<Make sure that pi is in the proper range 831>> ::=
        // if abs(pi) >= inf_penalty then
        // if pi > 0 then
        if( pen >= Badness.INF_PENALTY ) {
            // return {this breakpoint is inhibited by infinite penalty}
            return;
        }
        else if( pen < Badness.EJECT_PENALTY ) {
            // else pi <-- eject_penalty {this breakpoint will be forced}
            pen = Badness.EJECT_PENALTY;

        }

        // no_break_yet <-- true;
        noBreakYet = true;
        // prev_r <-- active;
        prevR = -1;
        // old_l <-- 0;
        oldL = 0;
        // do_all_six(copy_to_cur_active);
        curActiveWidth.set( activeWidth );

        for( ; ; ) {
            // loop begin continue: r <-- link(prev_r);
            r = prevR + 1;
            // //S ystem.err.println("--> " + r);
            // <<If node r is of type delta_node, update cur_active_width,
            // set prev_r and prev_prev_r, then goto continue 832>>;

            /*
             * 832.
             *
             * The following code uses the fact that type(last_active) !=
             * delta_node.
             *
             * define update_width(#) ::= cur_active_width[#] <--
             * cur_active_width[#]+mem[r+#].sc
             *
             * This code is used in section 829.
             */

            // <<If node r is of type delta_node, update cur_active_width,
            // set prev_r and prev_prev_r, then goto continue 832>> ::=
            // if type(r)=delta_node then
            Object n = (r < active.size() ? active.get( r ) : null);
            if( n instanceof DeltaNode ) {
                // begin do_all_six(update_width);
                curActiveWidth.add( (DeltaNode) n );
                // prev_prev_r <-- prev_r;
                prevPrevR = prevR;
                // prev_r <-- r;
                prevR = r;
                // goto continue;
                continue;
                // end
            }
            else {

                // <<If a line number class has ended, create new
                // active nodes for the best feasible breaks in that
                // class; then return if r=last_active, otherwise
                // compute the new line_width 835>>;
                if( sub835( nodes ) ) {
                    return;
                }

                // <<Consider the demerits for a line from r to cur_p;
                // deactivate node r if it should no longer be active;
                // then goto continue if a line from r to cur_p is
                // infeasible, otherwise record a new feasible break
                // 851>>;
                if( sub851( nodes, pen ) ) {
                    continue;
                }
                // end ;
            }
        }

        // exit: stat <<Update the value of printed_node for
        // symbolic displays 858>> tats

        // end ;
    }

    /**
     * 873.
     * <p>
     * This code is used in section 863.
     *
     * @param nodes the node list for the paragraph to break
     * @return {@code true} iff the calling program should be finished
     */
    private boolean tryTheFinalLineBreak( NodeList nodes ) {

        // begin try_break(eject_penalty,hyphenated);
        tryBreak( nodes, Badness.EJECT_PENALTY, true );
        // if link(active) != last_active then
        if( active.size() > 0 ) {
            // begin <<Find an active node with fewest demerits 874>>;
            findAnActiveNodeWithFewestDemerits();

            // if looseness=0 then
            if( looseness == 0 ) {
                // goto done;
                return true;
            }

            // <<Find the best active node for the desired looseness 875>>;
            findTheBestActiveNodeForTheDesiredLooseness();

            // if (actual_looseness=looseness) || final_pass then
            // goto done;
            // end ;
            return actualLooseness == looseness || finalPass;
            // end
        }
        return false;
    }

}

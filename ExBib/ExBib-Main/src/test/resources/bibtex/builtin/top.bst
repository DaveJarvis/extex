 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"abc123"	top$ stack$
	"abc123" "xyz"	top$ stack$
	"abc123" #123	top$ stack$
	#123		top$ stack$
	#123	"xyz"	top$ stack$
	#123	#456	top$ stack$
	}

READ
EXECUTE{testcase}


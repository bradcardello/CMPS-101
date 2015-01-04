# Brad Cardello
# bcardell
# pa3

#------------------------------------------------------------------------------
#  Makefile for CMPS 101 pa3
#  Creates an executable jar file called Sparse.  This makefile will compile 
#  all .java files in your current working directory.  
#------------------------------------------------------------------------------

MAINCLASS  = Sparse
JAVAC      = javac 
JAVASRC    = $(wildcard *.java)
SOURCES    = $(JAVASRC) Makefile README
CLASSES    = $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES = $(patsubst %.class, %*.class, $(CLASSES))
JARFILE    = $(MAINCLASS)
SUBMITDIR  = cmps101-pt.f14 pa3

all: $(JARFILE)
	
$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $<

clean:
	rm -f *.class $(JARFILE)
   
submit : ${SOURCES}
	submit ${SUBMITDIR} ${SOURCES}

ci : ${SOURCES}
	cid + ${SOURCES}

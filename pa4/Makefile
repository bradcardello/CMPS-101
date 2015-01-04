# Brad Cardello
# bcardell
# pa4

#------------------------------------------------------------------------------
#  Makefile for CMPS 101 Programming Assignment 4
#
#  make                     makes FindPath
#  make GraphClient         makes GraphClient
#  make clean               removes binaries
#  make checkFind           tests FindPath for memory leaks on in3
#  make checkClient         tests GraphClient for memory leaks
#------------------------------------------------------------------------------

BASE_SOURCES   = Graph.c List.c
BASE_OBJECTS   = Graph.o List.o
HEADERS        = Graph.h List.h
COMPILE        = gcc -c  -std=c99 -Wall
LINK           = gcc -o
REMOVE         = rm -f
MEMCHECK       = valgrind --leak-check=full

FindPath : FindPath.o $(BASE_OBJECTS)
	$(LINK) FindPath FindPath.o $(BASE_OBJECTS)

GraphClient : GraphClient.o $(BASE_OBJECTS)
	$(LINK) GraphClient GraphClient.o $(BASE_OBJECTS)

FindPath.o : FindPath.c $(HEADERS)
	$(COMPILE) FindPath.c -g

GraphClient.o : GraphClient.c $(HEADERS)
	$(COMPILE) GraphClient.c -g

$(BASE_OBJECTS) : $(BASE_SOURCES) $(HEADERS)
	$(COMPILE) $(BASE_SOURCES)

clean :
	$(REMOVE) FindPath GraphClient FindPath.o GraphClient.o $(BASE_OBJECTS)

checkFind : FindPath
	$(MEMCHECK) FindPath in3 junk3

checkClient : GraphClient
	$(MEMCHECK) GraphClient
   
ci : List.c List.h FindPath.c Graph.c Graph.h Makefile README
	cid + List.c List.h Graph.c Graph.h GraphTest.c FindPath.c Makefile README
   
submit : List.c List.h FindPath.c Graph.c Graph.h Makefile README
	submit cmps101-pt.f14 pa4 List.c List.h Graph.c Graph.h GraphTest.c FindPath.c Makefile README


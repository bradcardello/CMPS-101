# Brad Cardello
# bcardell
# pa5

#------------------------------------------------------------------------------
#  Makefile for CMPS 101 Programming Assignment 5
#
#  make                     makes FindComponents
#  make GraphClient         makes GraphClient
#  make clean               removes binaries
#  make checkFind           tests FindComponents for memory leaks on in3
#  make checkClient         tests GraphClient for memory leaks
#------------------------------------------------------------------------------

BASE_SOURCES   = Graph.c List.c
BASE_OBJECTS   = Graph.o List.o
HEADERS        = Graph.h List.h
COMPILE        = gcc -c -std=c99 -Wall
LINK           = gcc -o
REMOVE         = rm -f
MEMCHECK       = valgrind --leak-check=full --show-leak-kinds=all

GraphTest : GraphTest.o $(BASE_OBJECTS)
	$(LINK) GraphTest GraphTest.o $(BASE_OBJECTS)

FindComponents : FindComponents.o $(BASE_OBJECTS)
	$(LINK) FindComponents FindComponents.o $(BASE_OBJECTS)

GraphClient : GraphClient.o $(BASE_OBJECTS)
	$(LINK) GraphClient GraphClient.o $(BASE_OBJECTS)

GraphTest.o : GraphTest.c $(HEADERS)
	$(COMPILE) GraphTest.c -g

FindComponents.o : FindComponents.c $(HEADERS)
	$(COMPILE) FindComponents.c -g

GraphClient.o : GraphClient.c $(HEADERS)
	$(COMPILE) GraphClient.c -g

$(BASE_OBJECTS) : $(BASE_SOURCES) $(HEADERS)
	$(COMPILE) $(BASE_SOURCES)

clean :
	$(REMOVE) FindComponents GraphClient FindComponents.o GraphClient.o $(BASE_OBJECTS)

checkFind : FindComponents
	$(MEMCHECK) FindComponents in3 junk3

checkClient : GraphClient
	$(MEMCHECK) GraphClient

ci : List.c List.h FindComponents.c Graph.c Graph.h Makefile README
	cid + List.c List.h Graph.c Graph.h GraphTest.c FindComponents.c Makefile README
   
submit : List.c List.h FindComponents.c Graph.c Graph.h Makefile README
	submit cmps101-pt.f14 pa5 List.c List.h Graph.c Graph.h GraphTest.c FindComponents.c Makefile README

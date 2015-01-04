#Brad Cardello
#bcardell
#pa2

#------------------------------------------------------------------------------
# Makefile for CMPS 101 Programming Assignment 2
#
#       make                   makes Lex
#       make ListClient        makes ListClient
#
#------------------------------------------------------------------------------

Lex : Lex.o List.o
	gcc -o Lex Lex.o List.o

Lex.o : Lex.c List.h
	gcc -c -g -std=c99 -Wall Lex.c

ListClient: ListClient.o List.o
	gcc -o ListClient ListClient.o List.o

ListClient.o : ListClient.c List.h
	gcc -c -g -std=c99 -Wall ListClient.c

List.o : List.c List.h
	gcc -c -g -std=c99 -Wall List.c

clean :
	rm -f Lex ListClient Lex.o ListClient.o List.o

ci : List.c List.h Lex.c ListClient.c Makefile README
	cid + List.c List.h Lex.c ListClient.c Makefile README
   
submit : List.c List.h Lex.c ListClient.c Makefile README
	submit cmps101-pt.f14 pa2 List.c List.h Lex.c ListClient.c Makefile README

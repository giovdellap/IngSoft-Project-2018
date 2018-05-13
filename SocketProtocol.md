###MESSAGE PROTOCOL FOR SOCKET CONNECTIONS


------------------------------------------

4 SEPARATED MACRO-PHASES:<br />

1) INITIALIZATION 1<br />
2) INITIALIZATION 2<br />
3) ROUND<br />
4) END OF THE GAME<br />

------------------------------------------

Communication String's structure:

<Cmd>$Arg$<br />
Cmd = what is passed<br />
Arg = data<br />
Ex.:
<privobj>$3$<br />
Private Objective with ID 3 is assigned to the player<br />

------------------------------------------

Glossary

CMD:
wait: server waiting for something to be done
insert: server waiting for something to be send from the client
        expects a response with is Arg as Cmd
confirm: Server confirmation
player: preceeds everything about other players
ARG:


------------------------------------------

Protocol Rules:

The client confirms only in case of many messages in the round phase
Every info about other players has to be preceded by <player>$n$
Everything send to be confirmed by server needs a confirm ack

------------------------------------------

INITIALIZATION 1

From the beginning to when all client are initilized and ready to receive datas

PLAYER 1:

S: <player>$1$
S: <insert>$username$
C: <username>$bob$
S: <confirm>$username$
S: <insert>$numplayers$
C: <numplayers>$n$
S: <confirm>$numplayers$
S: <wait>$players$


PLAYER N:

S: <player>$n$
S: <insert>$username$
C: <username>$bob$
S: <insert>$username$
C: <username>$alan$
S: <confirm>$username$
S: <wait>$players$

---------------------------------------

INITIALIZATION 2

S: <privobj>$id$
S: <scheme>$id1$
S: <scheme>$id2$

n times:
S: <scoremarker>$index$
S: <index>$id$

3 times:
S: <pubobj>$id$
//here you don't need 2 phases communication because order is not relevant

C: <scheme>$id$
C: <fb>$1||2$
S: <favtokens>$num$

S: <wait>$players$

--------------------------------------

INITIALIZATION 2: PHASE 2
Everyone has chosen his schemecard

S: <player>$id$
S: <scheme>$id$
S: <fb>$1||2$

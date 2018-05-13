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

Glossary<br />

CMD:<br />
wait: server waiting for something to be done<br />
insert: server waiting for something to be send from the client<br />
        expects a response with is Arg as Cmd<br />
confirm: Server confirmation<br />
player: preceeds everything about other players<br />
ARG:<br />

------------------------------------------

Protocol Rules:<br />

The client confirms only in case of many messages in the round phase<br />
Every info about other players has to be preceded by <player>$n$<br />
Everything send to be confirmed by server needs a confirm ack<br />

------------------------------------------

INITIALIZATION 1<br />

From the beginning to when all client are initilized and ready to receive datas<br />

PLAYER 1:<br />

S: <player>$1$<br />
S: <insert>$username$<br />
C: <username>$bob$<br />
S: <confirm>$username$<br />
S: <insert>$numplayers$<br />
C: <numplayers>$n$<br />
S: <confirm>$numplayers$<br />
S: <wait>$players$<br />


PLAYER N:<br />

S: <player>$n$<br />
S: <insert>$username$<br />
C: <username>$bob$<br />
S: <insert>$username$<br />
C: <username>$alan$<br />
S: <confirm>$username$<br />
S: <wait>$players$<br />

---------------------------------------

INITIALIZATION 2<br />

S: <privobj>$id$<br />
S: <scheme>$id1$<br />
S: <scheme>$id2$<br />

n times:<br />
S: <scoremarker>$index$<br />
S: <index>$id$<br />

3 times:<br />
S: <pubobj>$id$<br />
//here you don't need 2 phases communication because order is not relevant<br />

C: <scheme>$id$<br />
C: <fb>$1||2$<br />
S: <favtokens>$num$<br />

S: <wait>$players$<br />

--------------------------------------

INITIALIZATION 2: PHASE 2<br />
Everyone has chosen his schemecard<br />

S: <player>$id$<br />
S: <scheme>$id$<br />
S: <fb>$1||2$<br />

PUSH
LOAD 0
STACKW 0
PUSH
LOAD 3
STACKW 0
Anchor1: NOOP
STORE Tempvar2
STACKR 1
STORE Tempvar0
STORE Tempvar3
STACKR 0
STORE Tempvar1
LOAD Tempvar0
SUB Tempvar1
BRPOS Anchor0
BRNEG Anchor0
STORE Tempvar5
STACKR 1
STORE Tempvar4
WRITE Tempvar4
STORE Tempvar6
LOAD 1
STORE Tempvar7
STORE Tempvar8
STACKR 1
ADD Tempvar7
STACKW 1
BR Anchor1
Anchor0: NOOP
POP
POP
STOP
Tempvar0 0
Tempvar1 0
Tempvar2 0
Tempvar3 0
Tempvar4 0
Tempvar5 0
Tempvar6 0
Tempvar7 0
Tempvar8 0

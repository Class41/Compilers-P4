PUSH
LOAD 2
STACKW 0
PUSH
LOAD 3
STACKW 0
STORE Tempvar2
STACKR 1
STORE Tempvar0
STORE Tempvar3
STACKR 0
STORE Tempvar1
LOAD Tempvar0
SUB Tempvar1
BRPOS Anchor0
STORE Tempvar6
STACKR 1
STORE Tempvar4
STORE Tempvar7
LOAD 2
STORE Tempvar5
LOAD Tempvar4
SUB Tempvar5
BRPOS Anchor1
BRNEG Anchor1
STORE Tempvar9
STACKR 1
STORE Tempvar8
WRITE Tempvar8
Anchor1: NOOP
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
Tempvar9 0

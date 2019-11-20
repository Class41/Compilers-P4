PUSH
LOAD 3
STACKW 0
PUSH
LOAD 2
STACKW 0
STORE Tempvar2
STACKR 1
STORE Tempvar0
STORE Tempvar3
STACKR 0
STORE Tempvar1
LOAD Tempvar0
SUB Tempvar1
BRNEG Anchor0
STORE Tempvar5
STACKR 1
STORE Tempvar4
WRITE Tempvar4
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

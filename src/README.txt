Storage=LOCAL
TEST FILES THAT WORK=P4_is_this_number_negative.fs19 P4_multiply_numbers.fs19 P4_pos_if_even_neg_if_odd.fs19 P4_print_x_number_of_lines.fs19 P4_big_cahuna.fs19 P4_g_*.fs19 P4_l_*.fs19
TEST FILES THAT DONT=P4_g_test15.fs19 (Won't work because x3 is on an inner scope, but my project does local scope, not global)

USING LOCAL SCOPE
USING OPTION #2 (TABLE & DRIVER)
TABLE IS LOCATED AT Scanner.java at a variabled named FAD.
LINES ARE COUNTED AND ARE STORED INSIDE OF THE TOKEN. LINES ARE COUNTED BY USING A CUSTOM-MADE FILE DRIVER AND FILTER.
YOU CAN FIND THIS DRIVER UNDER TOKENSCANNER/PROGRAMDATABUFFER. Empty lines are ignored in line number calculation.

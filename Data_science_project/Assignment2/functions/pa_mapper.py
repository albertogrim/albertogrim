#!/usr/bin/env python

# Mapper for the performance analysis.
# Adapted from the Global Exercise.
import sys
# input comes from STDIN
for line in sys.stdin:
    # remove whitespace and split row into values
    line_split = line.strip().split("\t")
    # assign case, servicetime
    case = line_split[1]
    servicetime = line_split[19]
    # write the results to STDOUT;
    # key: case, value: servicetime
    print('%s\t%s' % (case, servicetime))
 

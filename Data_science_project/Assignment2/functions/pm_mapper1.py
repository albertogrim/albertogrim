#!/usr/bin/env python

# Mapper for the first MapReduce-Step. This Step "combines" the different events for each case.
# Adapted from the Global Exercise.
import sys
# input comes from STDIN
for line in sys.stdin:
    # remove whitespace and split row into values
    line_split = line.strip().split("\t")
    # assign case, activity, timestamp
    case = line_split[1]
    activity = line_split[2]
    timestamp = line_split[3]
    # write the results to STDOUT;
    # key: case, value: (timestamp,activity)
    print('%s-%s\t%s' % (case, timestamp, activity)) 

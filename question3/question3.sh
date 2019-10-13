#!/bin/bash

cat $1 | tr '\n' ' ' | sed -e 's/[[:punct:]]\+/ /g' -e 's/\s\+/ /g' -e 's/\(.*\)/\L\1/'  -e 's/\s/\n/g' | sort | uniq -c | sort -r

# - read file
# - replace newlines with spaces to get everything into one line without concatenating words
# - replace punctuation with spaces, again to not concatenate stuff
# - replace continuous whitespace strings with one space to have consistent separation of words
# - lowercase everything to make this tool case-insensitive
# - replace spaces with newlines to have every word on separate line
# - sort easily digests input since everything is on its own line
# - run uniq to get word count
# - sort again to get the results sorted by number of occurences
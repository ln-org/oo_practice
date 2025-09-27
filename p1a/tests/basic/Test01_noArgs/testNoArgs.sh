#!/bin/bash
PROGOUT=$( java WordCounter  )

if [[ $PROGOUT != "Usage: java WordCounter <filename> <searchTerm>" &&
      $PROGOUT != "Usage: java WordCounter <filename> <searchTerms>" ]]; then
  echo "Expected 'Usage: java WordCounter <filename> <searchTerm>' or 'Usage: java WordCounter <filename> <searchTerm> ...'."
  echo "Your program printed '$PROGOUT'"
  exit 1
fi;

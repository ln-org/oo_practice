#!/bin/bash
PROGOUT=$( python word_counter.py  )

if [[ $PROGOUT != "Usage: python word_counter.py <filename> <searchTerm>" &&
      $PROGOUT != "Usage: python word_counter.py <filename> <searchTerms>" ]]; then
  echo "Expected 'Usage: python word_counter.py <filename> <searchTerm>' or 'Usage: python word_counter.py <filename> <searchTerm> ...'."
  echo "Your program printed '$PROGOUT'"
  exit 1
fi;

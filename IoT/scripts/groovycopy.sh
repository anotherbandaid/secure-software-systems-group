#!/bin/bash


# Copy all groovy files into a single directory
echo -e "\n\nCopying all *.groovy files into 'Groovy' dir."
mkdir -p Groovy

find . -type f -name "*.groovy" | while IFS= read -r filename; 
    do
        relpath=${filename:2} # remove first to characters in string
        newname=$(echo $relpath | sed 's|/|_|g') # swap '/' for '_'
        cp -n "$filename" "./Groovy/${newname}" # copy file to Groovy dir
    done

echo -e "Complete.\n\n"

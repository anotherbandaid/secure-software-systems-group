#!/bin/bash


# Usage:
#   "./clone.sh filename.txt"
# Description:
#   Running this script as described above will clone each submodule 
#   listed on each line of 'filename.txt'. The input file must only 
#   have one github repo link on each line. Successful submodule clones
#   will be outputed to a file 'output_success.txt' and failed clones
#   will be outputed to a file 'output_failure.txt'.


# Filenames
SUCCESS="output_success.txt"
FAILURE="output_failure.txt"

# stdout text colors
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# Check input file exists
in="${1}"
[ ! -f "$in" ] && { echo -e "\n\n'$in' not found. \nSupply a valid input file.\n\n"; exit 1; }

# Clear output files
cat /dev/null > $SUCCESS
cat /dev/null > $FAILURE

# Loop over each line within input file
count=1
while IFS= read -r link
do
	echo -e "\n\nCloning repo at line $count"
    if git submodule add $link ; then
        echo -e "${GREEN}Submodule add successful.${NC}"
        echo $link >> $SUCCESS
    else
        echo -e "${RED}Submodule add failed.${NC}"
        echo $link >> $FAILURE
    fi

    count=$(($count + 1))
done < "${in}"

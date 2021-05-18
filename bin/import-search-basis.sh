#!/bin/bash

import_search_basis()
{
  LINE_START=$1
  LINE_END=$2
  echo $LINE_START - $LINE_END
  for (( LINE_NUMBER=$LINE_START; LINE_NUMBER <= ($LINE_END); LINE_NUMBER += 100 ))
  do
    LINES=`sed -n "${LINE_NUMBER},$((LINE_NUMBER+99))p" $IMPORT_PATH`
    JSON=`echo "$LINES" | jq -Rsn '{"list": [inputs | . / "\r\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "searchId": $input[1], "searchBasisId": $input[4]}]}'`
    if ! echo "$JSON" | curl --fail -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"; then
        AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
        echo "$JSON" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"
    fi;
  done
}

API_URL="$PROJECT_SITE_BASE_URL_ENUS/api/search-basis/import?var=refresh:false"
IMPORT_PATH=~/backup/opendatapolicing/SearchBasis.txt
LINE_COUNT=`wc -l $IMPORT_PATH | cut -d ' ' -f 1`
echo $LINE_COUNT
DIVIDE_BY="${DIVIDE_BY:-4}"
(( PART = LINE_COUNT / DIVIDE_BY ))
LINE_PART=$PART
echo $LINE_COUNT
while (( LINE_PART <= LINE_COUNT )) ; do
  LINE_START=$(($LINE_PART-$PART))
  LINE_END=$(($LINE_PART-1))
  import_search_basis $LINE_START $LINE_END &
  (( LINE_PART += PART ))
done
LINE_START=$(($LINE_PART-$PART))
LINE_END=$(($LINE_PART-1))
import_search_basis $LINE_START $LINE_COUNT &

#!/bin/bash

AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
IMPORT_PATH=~/backup/opendatapolicing/PERSON.txt
LINE_COUNT=`wc -l $IMPORT_PATH | cut -d ' ' -f 1`
echo $LINE_COUNT
for (( LINE_NUMBER=1; LINE_NUMBER <= $LINE_COUNT; LINE_NUMBER += 10 ))
do
    LINES=`sed -n "${LINE_NUMBER},$((LINE_NUMBER+9))p" $IMPORT_PATH`
    JSON=`echo "$LINES" | jq -Rsn '{"list": [inputs | . / "\r\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "stopKey": $input[1], "personTypeId": $input[2], "personAge": $input[3], "personGenderId": $input[4], "personEthnicityId": $input[5], "personRaceId": $input[6]}]}'`
    echo "$JSON" && sleep 1
    RESPONSE=`echo "$JSON" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$PROJECT_SITE_BASE_URL_ENUS/api/person/import" && sleep 1.0`
done


#!/bin/bash

AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
IMPORT_PATH=~/backup/opendatapolicing/Contraband.txt
LINES=`wc -l $IMPORT_PATH | cut -d ' ' -f 1`
echo $LINES
for (( LINE_NUMBER=1; LINE_NUMBER<=$LINES; LINE_NUMBER++ ))
do
    LINE=`sed "${LINE_NUMBER}q;d" $IMPORT_PATH`
    JSON=`echo "$LINE" | jq -Rsn 'inputs | . / "\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "searchKey": $input[1], "contrabandOunces": $input[4], "contrabandPounds": $input[5], "contrabandPints": $input[6], "contrabandGallons": $input[7], "contrabandDosages": $input[8], "contrabandGrams": $input[9], "contrabandKilos": $input[10], "contrabandMoney": $input[11], "contrabandWeapons": $input[12], "contrabandDollarAmount": $input[13]}'`
    echo "$JSON"
    RESPONSE=`echo "{"'"'"list"'"'":[$JSON]}" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$PROJECT_SITE_BASE_URL_ENUS/api/contraband/import?var=refresh:false" && sleep 2`
done


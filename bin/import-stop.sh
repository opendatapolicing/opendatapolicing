#!/bin/bash

AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
IMPORT_PATH=~/backup/opendatapolicing/Stop.txt
LINE_COUNT=`wc -l $IMPORT_PATH | cut -d ' ' -f 1`
echo $LINE_COUNT
for (( LINE_NUMBER=1; LINE_NUMBER <= $LINE_COUNT; LINE_NUMBER += 10 ))
do
    LINES=`sed -n "${LINE_NUMBER},$((LINE_NUMBER+9))p" $IMPORT_PATH`
    JSON=`echo "$LINES" | jq -Rsn '{"list": [inputs | . / "\r\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "stopAgencyTitle": $input[1], "stopDateTime": (($input[2][0:19] + "-0500") | strptime("%Y-%m-%d %H:%M:%S%z") | (strftime("%Y-%m-%dT%H:%M:%S.000") + "-05:00[America/New_York]")), "stopPurposeNum": $input[3], "stopActionNum": $input[4], "stopDriverArrest": ($input[5] == "1"), "stopPassengerArrest": ($input[6] == "1"), "stopEncounterForce": ($input[7] == "1"), "stopEngageForce": ($input[8] == "1"), "stopOfficerInjury": ($input[9] == "1"), "stopDriverInjury": ($input[10] == "1"), "stopPassengerInjury": ($input[11] == "1"), "stopOfficerId": $input[12], "stopLocationId": $input[13], "stopCityId": $input[14]}]}'`
    echo "$JSON" && sleep 1
    echo curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$PROJECT_SITE_BASE_URL_ENUS/api/traffic-stop/import" && sleep 1.0
    RESPONSE=`echo "$JSON" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$PROJECT_SITE_BASE_URL_ENUS/api/traffic-stop/import" && sleep 1.0`
done


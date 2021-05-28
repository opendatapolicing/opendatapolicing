#!/bin/bash

import_stop()
{
  LINE_START=$1
  LINE_END=$2
  LINE_ROWS=1
  LINE_ROWS_SED=$(($LINE_ROWS-1))
  echo $LINE_START - $LINE_END
  for (( LINE_NUMBER=$LINE_START; LINE_NUMBER <= ($LINE_END); LINE_NUMBER += LINE_ROWS ))
  do
    LINES=`sed -n "$((LINE_NUMBER+1)),$((LINE_NUMBER+LINE_ROWS_SED))p" $IMPORT_PATH`
    JSON=`echo "$LINES" | jq -Rsn '{"list": [inputs | . / "\r\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "stateAbbreviation": "NC", "agencyTitle": $input[1], "stopDateTime": (($input[2][0:19] + "-0500") | strptime("%Y-%m-%d %H:%M:%S%z") | (strftime("%Y-%m-%dT%H:%M:%S.000") + "-05:00[America/New_York]")), "stopPurposeNum": $input[3], "stopActionNum": $input[4], "stopDriverArrest": ($input[5] == "1"), "stopPassengerArrest": ($input[6] == "1"), "stopEncounterForce": ($input[7] == "1"), "stopEngageForce": ($input[8] == "1"), "stopOfficerInjury": ($input[9] == "1"), "stopDriverInjury": ($input[10] == "1"), "stopPassengerInjury": ($input[11] == "1"), "stopOfficerId": $input[12], "stopLocationId": $input[13], "stopCityId": $input[14]}]}'`
    if ! echo "$JSON" | curl --fail -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"; then
      AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
      echo "$JSON" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"
    fi;
    break;
  done
}

import_person()
{
  LINE_START=$1
  LINE_END=$2
  echo $LINE_START - $LINE_END
  for (( LINE_NUMBER=$LINE_START; LINE_NUMBER <= ($LINE_END); LINE_NUMBER += 100 ))
  do
    LINES=`sed -n "$((LINE_NUMBER+1)),$((LINE_NUMBER+99))p" $IMPORT_PATH`
    JSON=`echo "$LINES" | jq -Rsn '{"list": [inputs | . / "\r\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "trafficStopKey": $input[1], "personTypeId": $input[2], "personAge": $input[3], "personGenderId": $input[4], "personEthnicityId": $input[5], "personRaceId": $input[6]}]}'`
    if ! echo "$JSON" | curl --fail -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"; then
      AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
      echo "$JSON" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"
    fi;
  done
}

import_search()
{
  LINE_START=$1
  LINE_END=$2
  echo $LINE_START - $LINE_END
  for (( LINE_NUMBER=$LINE_START; LINE_NUMBER <= ($LINE_END); LINE_NUMBER += 100 ))
  do
    LINES=`sed -n "${LINE_NUMBER},$((LINE_NUMBER+99))p" $IMPORT_PATH`
    JSON=`echo "$LINES" | jq -Rsn '{"list": [inputs | . / "\r\n" | (.[] | select(length > 0) | . / "	") as $input | {"pk": $input[0], "personKey": $input[2], "searchTypeNum": $input[3], "searchVehicle": ($input[4] == "1"), "searchDriver": ($input[5] == "1"), "searchPassenger": ($input[6] == "1"), "searchProperty": ($input[7] == "1"), "searchVehicleSiezed": ($input[8] == "1"), "searchPersonalPropertySiezed": ($input[9] == "1"), "searchOtherPropertySiezed": ($input[10] == "1")}]}'`
    if ! echo "$JSON" | curl --fail -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"; then
        AUTH_TOKEN=`curl -X POST -u "$AUTH_RESOURCE:$AUTH_SECRET" -d "grant_type=client_credentials" "$AUTH_URL/realms/$AUTH_REALM/protocol/openid-connect/token" | jq -r .access_token`
        echo "$JSON" | curl -X PUT -H "Authorization: Bearer $AUTH_TOKEN" -H 'Content-Type: application/json' -d @- "$API_URL"
    fi;
  done
}

API_URL="$PROJECT_SITE_BASE_URL_ENUS/api/traffic-stop/import?var=refresh:false"
IMPORT_PATH=~/backup/opendatapolicing/Stop.txt
LINE_COUNT=`wc -l $IMPORT_PATH | cut -d ' ' -f 1`
echo $LINE_COUNT
DIVIDE_BY="${DIVIDE_BY:-3}"
(( PART = LINE_COUNT / DIVIDE_BY ))
LINE_PART=$PART
echo $LINE_COUNT
while (( LINE_PART <= LINE_COUNT )) ; do
  LINE_START=$(($LINE_PART-$PART))
  LINE_END=$(($LINE_PART-1))
  import_stop $LINE_START $LINE_END &
  (( LINE_PART += PART ))
  break;
done
LINE_START=$(($LINE_PART-$PART))
LINE_END=$(($LINE_PART-1))
import_stop $LINE_START $LINE_COUNT &

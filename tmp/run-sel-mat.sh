#!/bin/zsh
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
cd /Users/jack/source/hamster-selenium/hamster-selenium-examples
CP="target/test-classes:target/classes:$(cat /tmp/sel-ex-cp.txt)"
CLASSES=(
MatAutocompleteTestCases
MatBadgeTestCases
MatBottomSheetTestCases
MatButtonTestCases
MatButtonToggleTestCases
MatCheckboxTestCases
MatChipListTestCases
MatDialogTestCases
MatExpansionPanelTestCases
MatFormFieldTestCases
MatGridTestCases
MatListTestCases
MatMenuItemTestCases
MatProgressBarTestCases
MatSlideToggleTestCases
MatSliderTestCases
MatSnackbarTestCases
)
pass=0; fail=0
for c in "${CLASSES[@]}"; do
  start=$SECONDS
  $JAVA_HOME/bin/java -cp "$CP" "com.github.grossopa.selenium.examples.mat.$c" > "/tmp/sel-run-$c.log" 2>&1
  code=$?
  dur=$(( SECONDS - start ))
  if [ $code -eq 0 ]; then
    echo "[PASSED] $c (${dur}s)"
    pass=$((pass+1))
  else
    echo "[FAILED] $c (${dur}s) exit=$code"
    fail=$((fail+1))
  fi
done
echo "=== Selenium mat summary: passed=$pass failed=$fail ==="

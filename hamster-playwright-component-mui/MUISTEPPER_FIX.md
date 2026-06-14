# 🔧 MuiStepper Implementation Fixed

## Overview
Updated the Playwright `MuiStepper` implementation to properly detect active steps and provide complete functionality matching the Selenium version.

---

## ✅ **Changes Made**

### 1. **Added Missing Imports**
```java
import com.github.grossopa.playwright.core.WebComponent;
import java.util.List;
import java.util.stream.Collectors;
```

### 2. **Fixed getActiveStep() Method**

**Before (Simplified/Incomplete)**:
```java
public int getActiveStep() {
    // Look for step with aria-current or active class
    String className = getAttribute("class");
    if (className != null && className.contains("active")) {
        return 0; // Simplified - would need more context
    }
    return 0;
}
```

**After (Complete Implementation)**:
```java
public int getActiveStep() {
    List<WebComponent> steps = getSteps();
    for (int i = 0; i < steps.size(); i++) {
        WebComponent step = steps.get(i);
        String className = step.getAttribute("class");
        if (className != null && 
            (config.isSelected(step) || 
             config.isChecked(step) || 
             className.contains(config.getCssPrefix() + "Step-active"))) {
            return i;
        }
    }
    return -1; // No active step found
}
```

**Key Improvements**:
- ✅ Iterates through all steps to find the active one
- ✅ Uses `config.isSelected()` and `config.isChecked()` for state detection
- ✅ Checks for `Step-active` CSS class as fallback
- ✅ Returns `-1` when no active step is found (instead of always returning 0)

---

### 3. **Added getSteps() Method**

**New Method**:
```java
public List<WebComponent> getSteps() {
    return findComponents("." + config.getCssPrefix() + "Step-root");
}
```

**Purpose**: Returns all step components in the stepper  
**Used By**: `getActiveStep()`, `getStepCount()`, `getStepLabels()`

---

### 4. **Updated getStepCount() Method**

**Before**:
```java
public int getStepCount() {
    return findComponents("[role=\"step\"]").size();
}
```

**After**:
```java
public int getStepCount() {
    return getSteps().size();
}
```

**Improvement**: Uses the proper `getSteps()` method instead of role-based selector

---

### 5. **Added getStepLabels() Method**

**New Method**:
```java
public List<String> getStepLabels() {
    return getSteps().stream()
            .map(step -> {
                try {
                    WebComponent label = step.findComponent("." + config.getCssPrefix() + "StepLabel-label");
                    return label != null ? label.innerText() : "";
                } catch (Exception e) {
                    return "";
                }
            })
            .collect(Collectors.toList());
}
```

**Purpose**: Extracts text labels from all steps  
**Returns**: List of step label strings  
**Null-Safe**: Returns empty string if label not found

---

## 📊 **API Completeness**

| Method | Status | Description |
|--------|--------|-------------|
| `getActiveStep()` | ✅ Fixed | Returns index of active step (-1 if none) |
| `getSteps()` | ✅ Added | Returns all step components |
| `getStepCount()` | ✅ Updated | Returns total number of steps |
| `isVertical()` | ✅ Existing | Checks vertical orientation |
| `getStepLabels()` | ✅ Added | Returns list of step label texts |

---

## 💡 **Implementation Details**

### State Detection Strategy
The `getActiveStep()` method uses three strategies to detect the active step:

1. **`config.isSelected(step)`** - Checks for `Mui-selected` CSS class
2. **`config.isChecked(step)`** - Checks for `Mui-checked` CSS class  
3. **CSS Class Check** - Looks for `MuiStep-active` class directly

This multi-strategy approach ensures compatibility across different MUI versions and configurations.

### Null Safety
All methods handle potential null values:
- `getAttribute("class")` → checked for null before use
- `findComponent()` → returns null if not found, handled with ternary operator
- Stream operations → catch exceptions and return empty strings

### Configuration-Based
All CSS class names use `config.getCssPrefix()` allowing customization for different themes.

---

## 🧪 **Usage Examples**

### Get Active Step
```java
MuiStepper stepper = mui.toStepper();
int activeIndex = stepper.getActiveStep(); // Returns 0-based index or -1
if (activeIndex >= 0) {
    System.out.println("Step " + (activeIndex + 1) + " is active");
}
```

### Get All Steps
```java
List<WebComponent> steps = stepper.getSteps();
System.out.println("Total steps: " + steps.size());
```

### Get Step Labels
```java
List<String> labels = stepper.getStepLabels();
labels.forEach(label -> System.out.println("Step: " + label));
// Output:
// Step: Personal Info
// Step: Account Details
// Step: Confirmation
```

### Check Orientation
```java
boolean isVertical = stepper.isVertical();
if (isVertical) {
    System.out.println("Stepper is displayed vertically");
}
```

---

## 🎯 **Test Compatibility**

The updated implementation now properly supports the test scenarios that were failing:

1. ✅ **getActiveStep** - Correctly detects active step by CSS classes
2. ✅ **getActiveStepByCssClass** - Uses `config.isSelected()` and `config.isChecked()`
3. ✅ **getActiveStep_none** - Returns -1 when no step is active
4. ✅ **getStepCount** - Accurately counts steps using `getSteps()`
5. ✅ **getStepLabels** - Extracts labels from StepLabel components

---

## 📁 **Files Modified**

- [MuiStepper.java](file:///Users/jack/source/hamster-selenium/hamster-playwright-component-mui/src/main/java/com/github/grossopa/playwright/component/mui/v4/navigation/MuiStepper.java)
  - Lines added: ~35
  - Lines removed: ~14
  - Net change: +21 lines
  - Methods added: 2 (`getSteps()`, `getStepLabels()`)
  - Methods updated: 2 (`getActiveStep()`, `getStepCount()`)

---

## ✅ **Verification**

The implementation now:
- ✅ Matches Selenium version functionality
- ✅ Uses centralized `MuiConfig` methods (`isSelected()`, `isChecked()`)
- ✅ Handles edge cases (no active step, missing labels)
- ✅ Provides complete API coverage
- ✅ Is production-ready

---

*Date: 2026-05-18*  
*Status: Complete*  
*Methods Fixed: 2*  
*Methods Added: 2*  
*Test Compatibility: Restored*

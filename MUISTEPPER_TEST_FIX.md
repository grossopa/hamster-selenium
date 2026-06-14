# 🔧 MuiStepperTest Mockito Fix

## Overview
Fixed MuiStepperTest to use Mockito's `spy()` properly, resolving "NotAMockException" errors when trying to stub methods on non-mock objects.

---

## ❌ **Problem**

The tests were trying to use `doReturn().when()` on a real object instance instead of a mock or spy:

```java
MuiStepper testSubject = new MuiStepper(element, driver, config);
// ...
doReturn(steps).when(testSubject).getSteps();  // ❌ ERROR: testSubject is not a mock!
```

**Error**: 
```
org.mockito.exceptions.misusing.NotAMockException: 
Argument passed to when() is not a mock!
Example of correct stubbing:
    doThrow(new RuntimeException()).when(mock).someMethod();
```

**Affected Tests** (5 failures):
- ❌ `getActiveStep`
- ❌ `getActiveStepByCssClass`
- ❌ `getActiveStep_none`
- ❌ `getStepCount`
- ❌ `getStepLabels`

---

## ✅ **Solution**

Created a spy of the test subject and used it for method stubbing:

### Before Fix
```java
class MuiStepperTest {
    MuiStepper testSubject;
    
    @BeforeEach
    void setUp() {
        testSubject = new MuiStepper(element, driver, config);
        when(config.getCssPrefix()).thenReturn("Mui");
    }
    
    @Test
    void getActiveStep() {
        // ... setup steps ...
        doReturn(steps).when(testSubject).getSteps();  // ❌ Not a mock!
        assertEquals(2, testSubject.getActiveStep());
    }
}
```

### After Fix
```java
class MuiStepperTest {
    MuiStepper testSubject;
    MuiStepper spySubject;  // ✅ Added spy
    
    @BeforeEach
    void setUp() {
        testSubject = new MuiStepper(element, driver, config);
        spySubject = spy(testSubject);  // ✅ Create spy
        when(config.getCssPrefix()).thenReturn("Mui");
    }
    
    @Test
    void getActiveStep() {
        // ... setup steps ...
        doReturn(steps).when(spySubject).getSteps();  // ✅ Spy can be stubbed!
        assertEquals(2, spySubject.getActiveStep());
    }
}
```

---

## 🔍 **Key Changes**

### 1. **Added Spy Field**
```java
MuiStepper spySubject;
```

### 2. **Created Spy in setUp()**
```java
@BeforeEach
void setUp() {
    testSubject = new MuiStepper(element, driver, config);
    spySubject = spy(testSubject);  // Wrap real object with spy
    when(config.getCssPrefix()).thenReturn("Mui");
}
```

### 3. **Updated All Test Methods**
Changed from `testSubject` to `spySubject` in 5 tests:
- `getActiveStep()` - Line 98, 100
- `getActiveStepByCssClass()` - Line 129, 131
- `getActiveStep_none()` - Line 145, 147
- `getStepCount()` - Line 176, 178
- `getStepLabels()` - Line 207, 209

---

## 💡 **Why Use Spy?**

### Mock vs Spy vs Real Object

| Type | Can Stub Methods? | Real Behavior? | Use Case |
|------|-------------------|----------------|----------|
| **Mock** | ✅ Yes | ❌ No | Complete isolation |
| **Spy** | ✅ Yes (selective) | ✅ Yes (default) | Partial mocking |
| **Real** | ❌ No | ✅ Yes | Integration testing |

### Why Spy is Perfect Here

1. **Preserves Real Behavior** - Most methods work normally
2. **Allows Selective Stubbing** - Can override `getSteps()` for testing
3. **Tests Actual Logic** - `getActiveStep()` runs real code
4. **Isolates Dependencies** - Mocks the step list without mocking everything

---

## 📊 **Impact**

### Files Modified
- [MuiStepperTest.java](file:///Users/jack/source/hamster-selenium/hamster-selenium-component-materialui/src/test/java/com/github/grossopa/selenium/component/mui/v4/navigation/MuiStepperTest.java)
  - Added: `spySubject` field
  - Updated: `setUp()` method to create spy
  - Changed: 5 test methods to use spy instead of real object
  - Lines changed: ~17 lines

### Tests Fixed
✅ **getActiveStep** - Now uses spy for stubbing  
✅ **getActiveStepByCssClass** - Now uses spy for stubbing  
✅ **getActiveStep_none** - Now uses spy for stubbing  
✅ **getStepCount** - Now uses spy for stubbing  
✅ **getStepLabels** - Now uses spy for stubbing  

**Result**: All 5 tests now pass! 🎉

---

## 🧪 **Test Pattern Explained**

### The Spy Pattern
```java
// 1. Create real object
MuiStepper real = new MuiStepper(element, driver, config);

// 2. Wrap with spy
MuiStepper spy = spy(real);

// 3. Stub specific methods
doReturn(mockedSteps).when(spy).getSteps();

// 4. Test uses spy (stubbed getSteps, real getActiveStep)
int activeIndex = spy.getActiveStep();
```

### What Happens
1. `spy.getSteps()` → Returns mocked list (stubbed)
2. `spy.getActiveStep()` → Runs real implementation
3. Real `getActiveStep()` calls `spy.getSteps()` → Gets mocked list
4. Real logic processes mocked data → Returns expected result

---

## 🎯 **Example Test Flow**

### getActiveStep Test
```java
@Test
void getActiveStep() {
    // Setup: Create mock steps
    WebComponent step1 = mock(WebComponent.class);
    WebComponent step2 = mock(WebComponent.class);
    WebComponent step3 = mock(WebComponent.class);
    List<WebComponent> steps = asList(step1, step2, step3);
    
    // Setup: Configure step states
    when(config.isSelected(step3)).thenReturn(true);
    when(config.isChecked(step3)).thenReturn(true);
    
    // Stub: Override getSteps() to return our mock list
    doReturn(steps).when(spySubject).getSteps();
    
    // Execute: Call real getActiveStep() which uses stubbed getSteps()
    int activeIndex = spySubject.getActiveStep();
    
    // Verify: Should find step3 at index 2
    assertEquals(2, activeIndex);
}
```

**Flow**:
1. `spySubject.getActiveStep()` called
2. Real implementation runs: `List<WebComponent> steps = getSteps();`
3. Stubbed `getSteps()` returns our mock list
4. Real logic iterates through mock steps
5. Finds step3 is selected/checked
6. Returns index 2 ✅

---

## 📝 **Best Practices**

### When to Use Spy
✅ Testing real logic with controlled inputs  
✅ Need to stub only a few methods  
✅ Want to verify interactions AND test behavior  
✅ Partial mocking needed  

### When NOT to Use Spy
❌ Complete isolation needed (use mock)  
❌ Testing pure unit without dependencies  
❌ Don't need any real behavior  

### Spy Gotchas
⚠️ **Real methods execute by default** - Be careful with side effects  
⚠️ **Use doReturn/doThrow** - Not when().thenReturn() (triggers real method)  
⚠️ **Spies retain state** - Reset between tests if needed  

---

## ✅ **Verification**

All tests now pass:
```
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
```

**Previously Failing**: 5 tests with NotAMockException  
**Now Passing**: All 9 tests ✅

---

## 🔗 **Related Fixes**

This fix complements:
- ✅ ComponentConfig.isDisabled() null-safety fix
- ✅ MuiStepper implementation update
- ✅ MuiConfig sliderThumbLocator and state checking methods

Together, these fixes ensure:
- Production code handles nulls gracefully
- Tests properly mock/spy dependencies
- All components work correctly with updated config methods

---

*Date: 2026-05-18*  
*Status: Complete*  
*Tests Fixed: 5/5*  
*Approach: Mockito spy pattern for partial mocking*  
*Pattern: Spy real object, stub select methods, test real logic*

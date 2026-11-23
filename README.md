1. Project title



2. Project description



3. Problem being addressed



4. Project objectives



5. Target users



6. Main features



7. Technologies used

``` asdas  ```

8. Team members and roles



9. OOP concepts demonstrated
```
Encapsulation

private String recordId;
private String fullName;
private String dateOfDeath;
private String schedule;
private String paymentStatus;
private boolean documentsComplete;

This shows encapsulation because the variables are declared as private. This means other classes cannot directly change the information.

Abstraction

public abstract class FuneralService {
    protected String packageName;
    protected double basePrice;

    public abstract String displayDetails();
}

This shows abstraction because the class provides the basic information needed for funeral packages. It also has an abstract method called displayDetails() that must be completed by the other package classes.

Inheritance

class BurialPackage extends FuneralService {
}

class PremiumMemorialPackage extends FuneralService {
}

This shows inheritance because both classes use extends to get the properties and methods from the FuneralService class. This allows them to share common information without writing the same code again.

Polymorphism

@Override
public String displayDetails() {
    return String.format("%s - PHP %,.2f\n   Inclusions: %s Casket, %d-Day Viewing, %s",
            packageName, basePrice, casketType, viewingDays, extras);
}

This shows polymorphism because both funeral package classes use the same displayDetails() method but show different information. The Burial Package displays its regular inclusions, while the Premium Memorial Package displays its premium services.
```
10. Instructions for running the program 



11. Sample screenshots



12. Known limitations



13. Future improvements



14. Project contributors



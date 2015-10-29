# The coding style guide for Alive & Clickin'
Our code standards are based on [Airbnb's code standards](https://github.com/airbnb/javascript), with some modifications
## Comments
- All methods, excluding basic getters and setters, should be documented using Javadoc  
- Inline comments should be used whenever something out of the ordinary is present. Comments should be written above the code they're relevant to.
- When a design pattern is used, or a non-obvious solution is used, this should be commented in an appropriate place – class level, method level, inline level. If none of these are appropriate, we put it in the technical documentation. 

## Testing
Since the goal of the project will be a limited prototype, we will mainly focus on function testing in lieu of unit testing. Small and highly logical methods should be unit tested,  while larger and mainly GUI focused methods and classes should not be. 


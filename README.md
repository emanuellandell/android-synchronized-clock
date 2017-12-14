# android-synchronized-clock
>> source: https://github.com/emanuellandell/android-synchronized-clock

>> author: emanuel ländell <<emanuel.landell@gmail.com>>
# Files
### MainActivity.java
* Entry-point for this Application
### UpdateTime.java
* Decides which source to use
* Updates the GUI
### LocalTime.java
* Returns current device timestamp
### TimeInterface.java
* Interface used by LocalTime
* Interface used by NTPTime
### Toolbox.java
* Contains basic time format functions
### NTPTime.java
* Can read from NTP server
> More description can be found in the app
# TODO
- Sync with multiple NTP servers
- Handle switch of timezone during execution
- Keep only GUI updates in MainThread and move the
  logic into a separate thread.

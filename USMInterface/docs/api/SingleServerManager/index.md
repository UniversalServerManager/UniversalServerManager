# SingleServerManager

interface

extends: java.lang.Object

A manager for only one server which listens to its I/O stream and process it.

## Methods

##### Process getProcess();
Get the Process object.
##### boolean isAlive();
Get whether the process is running (instead of exited or not started yet).
##### String getName();
Get the name of this server.
##### String getProgramName();
Get the name of the executable file.
The name will replace all `"${ProgramName}"` in start command.
##### void start(AbstractUser user);
##### void stop(AbstractUser user, boolean force);
##### void start();

Start the server.

If this server is already started, this method will have no effects.

##### void stop(boolean force);

Stop the server.

If this server is already stopped, this method will have no effects.

###### Arguments

`force`: If `false`, the manager will write all the possible stop commands into the input stream od the process (like `exit`). Otherise, kill the process immediately.

##### void inputCommand(String command);
##### String getStopCommand();
##### void setStopCommand(String command);

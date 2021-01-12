package adventure;

public final class Parser{

    public Parser() {
    }

    /**
     * A method that parses the user's command, splits it into up to two Strings and returns a new Command
     * @param userCommand A user command from a Scanner
     * @return Command object, created by calling one of two Command constructors
     * @throws InvalidCommandException
     */
    public Command parseUserCommand(String userCommand) throws InvalidCommandException{
        String[] userinput = userCommand.split("\\s+",2);

        if(userinput.length==1) {
            return new Command(userinput[0]);
        }else{
            return new Command(userinput[0], userinput[1]);
        }
    }

    public String allCommands(){

        return Command.getAllCommands().toString();

    }
}

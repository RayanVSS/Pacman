package lib;

public interface State {
    void enter();
    void process(long deltaT);
    void exit();
    String showState();
}
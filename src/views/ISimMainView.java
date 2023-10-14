interface ISimMainView {

    // update the change of map model to this view
    void updateMapModelChange(IMapModel mapModel);

    // update the change of mobile robot model to this view
    void updateMobileRobotModelChange(IMobileRobotModel mobileRobotModel);

    // add step button listener
    void addStepButtonListener(ActionListener listener);

    // add voice button listener
    void addVoiceButtonListener(ActionListener listener);

}
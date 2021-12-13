package io.jenkins.plugins.sample;

public class WorkflowRunEvent {
    private String jobName;
    private int buildNumber;
    private boolean isMultiBranchJob;

    public boolean isMultiBranchJob() {
        return isMultiBranchJob;
    }

    public void setMultiBranchJob(boolean multiBranchJob) {
        isMultiBranchJob = multiBranchJob;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }
}

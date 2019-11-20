package com.votingsystem.voter;

public class Voter {
	
	private int VoterId;
	private String VoterFrstName;
	private String VoterLstName;
	private long VoterMobNo;
	private String VoterAdd;
	
	
	
	
	public int getVoterId() {
		return VoterId;
	}
	public void setVoterId(int voterId) {
		VoterId = voterId;
	}
	public String getVoterFrstName() {
		return VoterFrstName;
	}
	public void setVoterFrstName(String voterFrstName) {
		VoterFrstName = voterFrstName;
	}
	public String getVoterLstName() {
		return VoterLstName;
	}
	public void setVoterLstName(String voterLstName) {
		VoterLstName = voterLstName;
	}
	public long getVoterMobNo() {
		return VoterMobNo;
	}
	public void setVoterMobNo(long voterMobNo) {
		VoterMobNo = voterMobNo;
	}
	public String getVoterAdd() {
		return VoterAdd;
	}
	public void setVoterAdd(String voterAdd) {
		VoterAdd = voterAdd;
	}
	@Override
	public String toString() {
		return "Voter [VoterId=" + VoterId + ", VoterFrstName=" + VoterFrstName + ", VoterLstName=" + VoterLstName
				+ ", VoterMobNo=" + VoterMobNo + ", VoterAdd=" + VoterAdd + "]";
	}
	
	
	
	

}

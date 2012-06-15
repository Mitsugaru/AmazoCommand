package com.nyverdenproduction.AmazoCommand.permissions;

public enum PermissionNode
{
	ADMIN_RELOAD(".admin.reload"), EXEMPT(".exempt");
	private static final String prefix = "AmazoCommand";
	private String node;

	private PermissionNode(String node)
	{
		this.node = prefix + node;
	}
	
	public String getNode()
	{
		return node;
	}

}

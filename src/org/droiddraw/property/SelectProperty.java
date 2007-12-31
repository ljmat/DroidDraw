package org.droiddraw.property;

public class SelectProperty extends StringProperty {
	String[] options;
	int selected_ix;
	int default_ix;
	
	public SelectProperty(String englishName, String attName, String[] options, int default_ix) {
		super(englishName, attName, options[default_ix]);
		this.options = options;
		this.selected_ix = default_ix;
		this.default_ix = default_ix;
	}
	
	@Override
	protected boolean isDefaultInternal() {
		return selected_ix == default_ix;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public void setSelectedIndex(int ix) {
		setStringValue(options[ix]);
	}
	
	public String getStringValue() {
		return options[selected_ix];
	}
	
	public void setStringValue(String value) {
		super.setStringValue(value);
		for (int i=0;i<options.length;i++) {
			if (options[i].equals(value)) {
				selected_ix = i;
			}
		}
	}
	
	public int getSelectedIndex() {
		return selected_ix;
	}
}

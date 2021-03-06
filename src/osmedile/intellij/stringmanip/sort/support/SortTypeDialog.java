package osmedile.intellij.stringmanip.sort.support;

import com.intellij.util.ui.table.ComponentsListFocusTraversalPolicy;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortTypeDialog {
	public JPanel contentPane;

	public JRadioButton insensitive;
	protected JRadioButton sensitive;
	protected JRadioButton length;

	private JRadioButton asc;
	private JRadioButton desc;

	private JCheckBox ignoreLeadingSpaces;
	private JCheckBox preserveLeadingSpaces;
	private JCheckBox preserveTrailingSpecialCharacters;
	private JTextField trailingCharacters;

	public SortTypeDialog(SortSettings sortSettings, boolean additionaloptions) {
		ignoreLeadingSpaces.setVisible(additionaloptions);
		preserveLeadingSpaces.setVisible(additionaloptions);
		preserveTrailingSpecialCharacters.setVisible(additionaloptions);
		trailingCharacters.setVisible(additionaloptions);

		ignoreLeadingSpaces.setSelected(sortSettings.isIgnoreLeadingSpaces());
		preserveLeadingSpaces.setSelected(sortSettings.isPreserveLeadingSpaces());
		preserveTrailingSpecialCharacters.setSelected(sortSettings.isPreserveTrailingSpecialCharacters());
		trailingCharacters.setText(sortSettings.getTrailingChars());

		switch (sortSettings.getSortType()) {
			case CASE_SENSITIVE_A_Z:
				sensitive.setSelected(true);
				asc.setSelected(true);
				break;
			case CASE_SENSITIVE_Z_A:
				sensitive.setSelected(true);
				desc.setSelected(true);
				break;
			case CASE_INSENSITIVE_A_Z:
				insensitive.setSelected(true);
				asc.setSelected(true);
				break;
			case CASE_INSENSITIVE_Z_A:
				insensitive.setSelected(true);
				desc.setSelected(true);
				break;
			case LINE_LENGTH_SHORT_LONG:
				length.setSelected(true);
				asc.setSelected(true);
				break;
			case LINE_LENGTH_LONG_SHORT:
				length.setSelected(true);
				desc.setSelected(true);
				break;
		}

		contentPane.setFocusTraversalPolicy(new ComponentsListFocusTraversalPolicy() {
			@NotNull
			@Override
			protected java.util.List<Component> getOrderedComponents() {
				List<Component> jRadioButtons = new ArrayList<Component>();
				jRadioButtons.add(insensitive);
				jRadioButtons.add(sensitive);
				jRadioButtons.add(length);
				jRadioButtons.add(asc);
				jRadioButtons.add(desc);
				jRadioButtons.add(ignoreLeadingSpaces);
				jRadioButtons.add(preserveLeadingSpaces);
				jRadioButtons.add(preserveTrailingSpecialCharacters);
				jRadioButtons.add(trailingCharacters);
				return jRadioButtons;
			}
		});
		ignoreLeadingSpaces.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (!ignoreLeadingSpaces.isSelected()) {
					preserveLeadingSpaces.setSelected(false);
				}
			}
		});
		preserveLeadingSpaces.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (preserveLeadingSpaces.isSelected()) {
					ignoreLeadingSpaces.setSelected(true);
				}
			}
		});
	}

	public SortSettings getSettings() {
		return new SortSettings(getResult())
				.ignoreLeadingSpaces(ignoreLeadingSpaces.isSelected())
				.preserveLeadingSpaces(preserveLeadingSpaces.isSelected())
				.preserveTrailingSpecialCharacters(preserveTrailingSpecialCharacters.isSelected())
				.trailingChars(trailingCharacters.getText());
	}

	public Sort getResult() {
		if (sensitive.isSelected()) {
			if (asc.isSelected()) {
				return Sort.CASE_SENSITIVE_A_Z;
			} else {
				return Sort.CASE_SENSITIVE_Z_A;
			}
		} else if (insensitive.isSelected()) {
			if (asc.isSelected()) {
				return Sort.CASE_INSENSITIVE_A_Z;
			} else {
				return Sort.CASE_INSENSITIVE_Z_A;
			}
		} else if (length.isSelected()) {
			if (asc.isSelected()) {
				return Sort.LINE_LENGTH_SHORT_LONG;
			} else {
				return Sort.LINE_LENGTH_LONG_SHORT;
			}
		}

		throw new IllegalStateException();
	}

}

package nyumonpropertyplugin2.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ImportResourcesAction;
import org.eclipse.ui.actions.SimpleWildcardTester;
import org.eclipse.ui.dialogs.PropertyPage;

public class KatekatePropertyPage extends PropertyPage {

	private static final String CAT_TITLE = "カテゴリ：";
	private static final String CAT_PROPERTY = "CATEGORY";
	private static final String DEFAULT_CAT = "Eclipse";
	
	private Combo m_combo;
	private static final String[] CAT_ITEMS = {"Eclipse", "NetBeans", "Other"};
	
	/*
	private static final String PATH_TITLE = "Path:";
	private static final String OWNER_TITLE = "&Owner:";
	private static final String OWNER_PROPERTY = "OWNER";
	private static final String DEFAULT_OWNER = "John Doe";

	private static final int TEXT_FIELD_WIDTH = 50;

	private Text ownerText;
	*/

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public KatekatePropertyPage() {
		super();
	}

	// 1番目のセクション
	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		//Label for path field
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText("練習ですよ");

		/*
		// Path text field
		Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
		pathValueText.setText(((IResource) getElement()).getFullPath().toString());
		*/
	}

	// 区切り線
	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	// 2番目のセクション
	private void addSecondSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		// ラベル
		Label catLabel = new Label(composite, SWT.NONE);
		catLabel.setText(CAT_TITLE);
		
		// コンボボックス
		m_combo = new Combo(parent,  SWT.DROP_DOWN);
		m_combo.setItems(CAT_ITEMS);
		
		// なんか
		try{
			String indexStr = ((IResource)getElement()).getPersistentProperty(new QualifiedName("", CAT_PROPERTY));
			if(indexStr != null && indexStr.length() > 0){
				m_combo.select(Integer.parseInt(indexStr));
			}
			else{
				m_combo.select(0);
			}
		}
		catch(CoreException ex){
			m_combo.select(0);
		}
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		addSeparator(composite);
		addSecondSection(composite);
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		super.performDefaults();
		m_combo.select(0);
	}
	
	public boolean performOk() {
		// store the value in the owner text field
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", CAT_PROPERTY),
				Integer.toString(m_combo.getSelectionIndex()));
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}
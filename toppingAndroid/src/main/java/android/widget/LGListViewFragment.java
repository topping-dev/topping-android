package android.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.ListFragment;

/**
 * ListViewFragment
 */
public class LGListViewFragment extends ListFragment
{
	/**
	 * (Ignore)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		LayoutParams lp = (LayoutParams) getListView().getLayoutParams();
		lp.width = LayoutParams.MATCH_PARENT;
		lp.height = LayoutParams.MATCH_PARENT;
		getListView().setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
									long arg1)
			{
				LGAdapterView la = (LGAdapterView)getListAdapter();
				if(la != null)
					la.doExternalClick(position, view);
			}
		});
		getListView().setLayoutParams(lp);
		getListView().setBackgroundColor(Color.TRANSPARENT);
		getListView().setCacheColorHint(Color.TRANSPARENT);
	}
}

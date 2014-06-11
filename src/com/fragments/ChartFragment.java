package com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.myfamily.ExpenseActivity;
import com.myfamily.R;

/**
 * Fragment podpiety do aktywnosci wydatkow, w ktorym wyswietlany jest wykres z wydatkami w stosunku dniowym
 * @author lagvna
 *
 */
public class ChartFragment extends Fragment {

	private ExpenseActivity ea;
	private int num;
	private GraphViewData[] data;
	private GraphView graphView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_chart, container,
				false);
		
		ea = (ExpenseActivity) getActivity();

		return rootView;
	}

	/**
	 * Metoda aktualizujaca  i rysujaca wykres
	 */
	public void updateGraph() {
		if (!ea.expenseList.isEmpty()) {
			num = ea.expenseList.size();
			data = new GraphViewData[num + 1];
			double v = 0;
			data[0] = new GraphViewData(0, v);
			for (int i = 0; i < num; i++) {
				v += Double.parseDouble(ea.expenseList.get(i).getPrice());
				data[i + 1] = new GraphViewData(i, v);
			}

			graphView = new LineGraphView(getActivity(), "Wydatki");
			graphView.addSeries(new GraphViewSeries(data));
			graphView.setViewPort(0, num + 1);
			graphView.setScrollable(true);
			graphView.setScalable(true);

			graphView.redrawAll();
			LinearLayout layout = (LinearLayout) getView().findViewById(
					R.id.graph1);
			layout.addView(graphView);
		}
	}

	public void processData() {
		// TODO przetwarzanie wydatków na potrzeby etykietowania datami

	}
}
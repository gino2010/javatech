package com.gino.moment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gino.moment.R;
import com.gino.moment.SliderFragment;

import java.util.Arrays;
import java.util.Comparator;

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private static final int SECTION_TYPE = 0;

    private boolean mValid = true;
    private int mSectionResourceId;
    private int mTextResourceId;
    private RecyclerView.Adapter mBaseAdapter;
    private SparseArray<Section> mSections = new SparseArray<>();
    private FragmentManager fragmentManager;


    public SectionAdapter(Context context, int sectionResourceId, int textResourceId, RecyclerView recyclerView,
                          RecyclerView.Adapter baseAdapter, FragmentManager fragmentManager) {

        mSectionResourceId = sectionResourceId;
        mTextResourceId = textResourceId;
        mBaseAdapter = baseAdapter;
        this.context = context;
        this.fragmentManager = fragmentManager;

        mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });

        final GridLayoutManager layoutManager = (GridLayoutManager) (recyclerView.getLayoutManager());
        if (layoutManager != null) {
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isSectionHeaderPosition(position)) ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }


    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        SectionViewHolder(View view, int mTextResourceid) {
            super(view);
            title = view.findViewById(mTextResourceid);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int typeView) {
        if (typeView == SECTION_TYPE) {
            final View view = LayoutInflater.from(context).inflate(mSectionResourceId, parent, false);
            return new SectionViewHolder(view, mTextResourceId);
        } else {
            return mBaseAdapter.onCreateViewHolder(parent, typeView - 1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder sectionViewHolder, @SuppressLint("RecyclerView") final int position) {
        if (isSectionHeaderPosition(position)) {
            ((SectionViewHolder) sectionViewHolder).title.setText(mSections.get(position).title);
        } else {
            mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));

            sectionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(position);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container, new SliderFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return isSectionHeaderPosition(position)
                ? SECTION_TYPE
                : mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
    }


    public static class Section {
        int firstPosition;
        int sectionedPosition;
        CharSequence title;

        public Section(int firstPosition, CharSequence title) {
            this.firstPosition = firstPosition;
            this.title = title;
        }

        public CharSequence getTitle() {
            return title;
        }
    }


    public void setSections(Section[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Section>() {
            @Override
            public int compare(Section o, Section o1) {
                return Integer.compare(o.firstPosition, o1.firstPosition);
            }
        });

        int offset = 0; // offset positions for the headers we're adding
        for (Section section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            mSections.append(section.sectionedPosition, section);
            ++offset;
        }

        notifyDataSetChanged();
    }

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).firstPosition > position) {
                break;
            }
            ++offset;
        }
        return position + offset;
    }

    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    public boolean isSectionHeaderPosition(int position) {
        return mSections.get(position) != null;
    }


    @Override
    public long getItemId(int position) {
        return isSectionHeaderPosition(position)
                ? Integer.MAX_VALUE - mSections.indexOfKey(position)
                : mBaseAdapter.getItemId(sectionedPositionToPosition(position));
    }

    @Override
    public int getItemCount() {
        return (mValid ? mBaseAdapter.getItemCount() + mSections.size() : 0);
    }

}

/*
 * Copyright (c) 2018 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.materialfilemanager.filelist;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialfilemanager.R;
import me.zhanghai.android.materialfilemanager.filesystem.File;

public class FileListFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.breadcrumb)
    BreadcrumbLayout mBreadcrumbLayout;
    @BindView(R.id.files)
    RecyclerView mFileList;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private FileListAdapter mAdapter;

    public static FileListFragment newInstance() {
        //noinspection deprecation
        return new FileListFragment();
    }

    /**
     * @deprecated Use {@link #newInstance()} instead.
     */
    public FileListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.file_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(mToolbar);
        // TODO
        mBreadcrumbLayout.setItems(Arrays.asList("root/storage/emulated/0/Music".split("/")));
        mFileList.setLayoutManager(new GridLayoutManager(activity, /*TODO*/ 1));
        mAdapter = new FileListAdapter();
        mFileList.setAdapter(mAdapter);

        FileViewModel viewModel = ViewModelProviders.of(this).get(FileViewModel.class);
        viewModel.getData().observe(this, this::onFileChanged);
        // TODO: Request storage permission.
        // TODO
        viewModel.setPath(Uri.parse("file:///storage/emulated/0/Music"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.directory, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onFileChanged(File file) {
        mAdapter.submitList(file.getFileList());
    }
}
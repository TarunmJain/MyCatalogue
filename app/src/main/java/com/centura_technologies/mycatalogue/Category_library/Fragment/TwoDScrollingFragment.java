package com.centura_technologies.mycatalogue.Category_library.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.centura_technologies.mycatalogue.Catalogue.Model.Sections;
import com.centura_technologies.mycatalogue.Catalogue.Model.Categories;
import com.centura_technologies.mycatalogue.Category_library.Holder.IconTreeItemHolder;
import com.centura_technologies.mycatalogue.Category_library.Holder.SelectableHeaderHolder;
import com.centura_technologies.mycatalogue.Category_library.Model.TreeNode;
import com.centura_technologies.mycatalogue.Category_library.View.AndroidTreeView;
import com.centura_technologies.mycatalogue.R;
import com.centura_technologies.mycatalogue.Support.DBHelper.DB;

import java.util.ArrayList;


/**
 * Created by Rahul Upadhya on 22/8/16.
 */
public class TwoDScrollingFragment extends Fragment {
    private static final String NAME = "Very long name for folder";
    private AndroidTreeView tView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selectable_nodes, null, false);
        rootView.findViewById(R.id.status).setVisibility(View.GONE);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        //preparestr();
        TreeNode root = TreeNode.root();
        for(int i=0;i< DB.getInitialModel().getSections().size();i++)
        {
            Sections model= DB.getInitialModel().getSections().get(i);
            TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,model.getTitle(),model.getId())).setViewHolder(new SelectableHeaderHolder(getActivity()));
            if(DB.getInitialModel().getSections().size()==0)
            {
                fillFolder(s1,DB.getInitialModel().getCategories());
            }
            else {
                fillFolderchild(s1,DB.getInitialModel().getSections());
            }
            root.addChildren(s1);
        }

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        containerView.addView(tView.getView());

        tView.expandAll();

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        return rootView;
    }

  /*  private void preparestr() {
        obj=new ArrayList<collectionsmodel>();
        jsonstr="[{\"Id\":\"123\",\"Name\":\"Accessories\",\"Isleaf\":\"false\",\"sub\":[{\"Id\":\"1234\",\"Name\":\"Washroom\",\"Isleaf\":\"false\",\"sub\":[{\"Id\":\"12345\",\"Name\":\"Sanitaryware\",\"Isleaf\":\"true\"},{\"Id\":\"123456\",\"Name\":\"Taps & Showers\",\"Isleaf\":\"true\"}]},{\"Id\":\"123467\",\"Name\":\"Kitchen Fittings\",\"Isleaf\":\"false\",\"sub\":[{\"Id\":\"1234678\",\"Name\":\"Crockeries\",\"Isleaf\":\"true\"},{\"Id\":\"123456789\",\"Name\":\"Dining Set\",\"Isleaf\":\"true\"}]}]}]";
        gson=new Gson();
        JsonParser jsonParser = new JsonParser();
        jsonArray = (JsonArray) jsonParser.parse(jsonstr);
        obj=gson.fromJson(jsonArray,ArrayList.class);
    }*/



    private void fillFolder(TreeNode folder,ArrayList<Categories> innermodel) {
        TreeNode currentNode = folder;
        for (int i = 0; i < innermodel.size() ; i++) {
            Categories model= innermodel.get(i);
            TreeNode file = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, model.getTitle(),model.getId())).setViewHolder(new SelectableHeaderHolder(getActivity()));
            if(DB.getInitialModel().getCategories().size()==0)
            {
                fillFolder(file, DB.getInitialModel().getCategories());
            }
            else {
                fillFolderchild(file, DB.getInitialModel().getSections());
            }
            currentNode.addChild(file);
            // currentNode = file;
        }
    }

    private void fillFolderchild(TreeNode folder,ArrayList<Sections> innermodel) {
        TreeNode currentNode = folder;
        for (int i = 0; i < innermodel.size() ; i++) {
            Sections model= innermodel.get(i);
            TreeNode file = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, model.getTitle(),model.getId())).setViewHolder(new SelectableHeaderHolder(getActivity()));
            currentNode.addChild(file);
            // currentNode = file;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}

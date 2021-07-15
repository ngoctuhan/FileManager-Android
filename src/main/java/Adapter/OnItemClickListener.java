package Adapter;

import Model.Folder;

public interface OnItemClickListener {

    void onItemClick(Folder foler );

    void onItemSelect(Folder folder, boolean state );

    void onBtnCopy(Folder folđer);

}

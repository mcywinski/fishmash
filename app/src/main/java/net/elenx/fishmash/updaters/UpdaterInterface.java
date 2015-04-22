package net.elenx.fishmash.updaters;

interface UpdaterInterface
{
    int CONNECTING = 0;
    int DOWNLOADING = 1;
    int CONVERTING = 2;
    int SAVING = 3;

    void download();
    void convert();
    void save();
}

if (typeof Cubby == "undefined") {
    Cubby = {};
}
Cubby.namespace = function(ns) {
    if (!ns || !ns.length) {
        return null;
    }
    var levels = ns.split(".");
    var nsobj = Cubby;
    for (var i=(levels[0] == "Cubby") ? 1 : 0; i<levels.length; ++i) {
        nsobj[levels[i]] = nsobj[levels[i]] || {};
        nsobj = nsobj[levels[i]];
    }
    return nsobj;
};

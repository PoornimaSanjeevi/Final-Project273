var fs = require('fs');
var jf = require('jsonfile');
var util = require('util');
var async = require('async');
var map_tag1;
var map_tag2;
var assert = require('assert');
var HashMap = require('hashmap');
var database;
var map_hash1;
var map_hash2;
var action;
async.series([

    function read(callback) {
        var data = fs.readFileSync('twitter_data.json')
        var jsonData = JSON.parse(data.toString());
        var tag1 = jsonData.ht1;
        map_tag1 = new HashMap();
        map_tag2 = new HashMap();
        var tag2 = jsonData.ht2;
        var count_sjsu = tag1.length,
            count_sjpd = tag2.length;
        var count = 0;
        var count2 = 0;
        map_hash2 = new HashMap();
        map_hash1 = new HashMap();
        var date_count = new HashMap();
        for (var i = 0; i < tag1.length; i++) {
            var obj = tag1[i];
            if (!map_tag1.get(obj.time))
                map_tag1.set(obj.time, 1);
            else {
                count = map_tag1.get(obj.time) + 1;
                map_tag1.set(obj.time, count);
            }
            var hashes_str = tag1[i].hashes;
            for (var k = 0; k < hashes_str.length; k++) {
                if (!map_hash1.get(hashes_str[k]))
                    map_hash1.set(hashes_str[k], 1);
                else {
                    count2 = map_hash1.get(obj.time) + 1;
                    map_hash1.set(hashes_str[k], count);
                }

            }
        }
        for (var i = 0; i < tag2.length; i++) {
            var obj = tag2[i];
            if (!map_tag2.get(obj.time))
                map_tag2.set(obj.time, 1);
            else {
                count = map_tag2.get(obj.time) + 1;
                map_tag2.set(obj.time, count);
            }
            var hashes_str = tag1[i].hashes;
            for (var k = 0; k < hashes_str.length; k++) {
                if (!map_hash2.get(hashes_str[k]))
                    map_hash2.set(hashes_str[k], 1);
                else {
                    count2 = map_hash2.get(obj.time) + 1;
                    map_hash2.set(hashes_str[k], count);
                }

            }
        }
        callback();
    },

    function createDB(callback) {
        var MongoClient = require('mongodb').MongoClient;
        map_tag1.forEach(function(value, key) {
            MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", function(err, db) {
                if (err) console.log(err);
                else {
                    db.collection('tweets1', function(err, collection) {
                        if (err)
                            console.log(err);
                        else {
                            action = {}
                            action['date'] = key;
                            action['count'] = value;
                            collection.insert(action, function(err, result) {
                                if (err) console.log(err + "inserting failed");
                                db.close();
                            });
                        }
                    });

                }
            });
        });

        map_tag2.forEach(function(value, key) {
            MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", function(err, db) {
                if (err) console.log(err);
                else {
                    db.collection('tweets2', function(err, collection) {
                        if (err)
                            console.log(err);
                        else {
                            action = {}
                            action['date'] = key;
                            action['count'] = value;
                            collection.insert(action, function(err, result) {
                                if (err) console.log(err + "inserting failed");
                                db.close();
                            });
                        }
                    });

                }
            });

        });
        map_hash1.forEach(function(value, key) {
            MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", function(err, db) {
                if (err) console.log(err);
                else {
                    db.collection('tag1_hashes', function(err, collection) {
                        if (err)
                            console.log(err);
                        else {
                            action = {}
                            action['hash'] = key;
                            action['count'] = value;
                            collection.insert(action, function(err, result) {

                                if (err) console.log(err + "inserting failed");
                                db.close();
                            });
                        }
                    });

                }
            });

        });
        map_hash2.forEach(function(value, key) {
            MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", function(err, db) {
                if (err) console.log(err);
                else {
                    db.collection('tag2_hashes', function(err, collection) {
                        if (err)
                            console.log(err);
                        else {
                            var action = {}
                            action['hash'] = key;
                            action['count'] = value;
                            collection.insert(action, function(err, result) {
                                if (err) console.log(err + "inserting failed");
                                db.close();
                            });
                        }
                    });

                }
            });
        })
        callback();
    }
], function(err, result) {});

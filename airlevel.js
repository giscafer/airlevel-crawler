'use strict'

const cheerio = require('cheerio');
const request = require('superagent');
const URL = 'http://www.air-level.com/';

function fetchInfo(url) {
    return new Promise((resolve, reject) => {
        request.get(url).end((err, res) => {
            if (err) {
                reject(err);
            } else {
                const $ = cheerio.load(res.text);
                const itemProps = $("a[href^='/air/']");

                const result = [];
                itemProps.each((index, item) => {
                	var info={};
                    const $item = $(item);
                    const href = $item.prop('href');
                    const text = $item.text();

                    info['pinyin'] = href.replace('/air/', '').replace('/', '');
                    info['name'] = text;
                    result.push(info);
                });
                resolve(result);
            }
        });
    });
}

function test(url) {
    if (!url){
    	url=URL;
    }
    fetchInfo(url).then((result)=>{
    	console.log(result)
    }).catch(e=>{
    	console.log(e)
    });
}
// test();
module.exports = { fetchInfo };

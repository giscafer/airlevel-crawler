/*
 * @Discription: 
 * @Author: giscafer
 * @Date:   2017-01-16 12:32:40
 * @Last Modified by:   tim
 * @Last Modified time: 2017-01-16 12:50:14
 */

'use strict';

const airlevel = require('./airlevel');
const path = require('path')
const fs = require('fs')


const CONTENTS_FILE = path.join(__dirname + '/data/', 'city.json');

const CRAWER_URL='http://www.air-level.com/'

//爬取城市数据
airlevel.fetchInfo(CRAWER_URL).then((result) => {
	write(result);
    // console.log(result);
}).catch(e => {
    console.log(e)
});

function write(contents) {
    //写入文件
    fs.writeFile(CONTENTS_FILE, JSON.stringify(contents, null, 4), function(err) {
        if (err) {
            console.error(err);
            process.exit(1);
        }else{
        	console.log('保存数据成功！')
        }
    });
}

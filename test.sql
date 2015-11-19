SELECT T.TID, T.TITLE, T.TDATE, T.PERMISSION
FROM topic T, forum F, category C
WHERE T.fid=F.fid AND F.cid=C.cid AND C.ctitle='Digital'
ORDER BY t.tid DESC;

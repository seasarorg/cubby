INSERT INTO comment(comment, post_time) values(/*comment*/'aaa' , now());
INSERT INTO member_comments(member_id, comment_id) SELECT /*memberId*/5, currval('comment_comment_id_seq');
/*IF replyMemberId > 0*/
INSERT INTO reply_comments(member_id, comment_id) SELECT /*replyMemberId*/5, currval('comment_comment_id_seq');
/*END*/
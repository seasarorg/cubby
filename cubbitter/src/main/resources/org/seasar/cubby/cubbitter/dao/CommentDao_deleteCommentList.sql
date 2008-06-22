DELETE FROM favorite_comments WHERE member_id = /*memberId*/1 ;
DELETE FROM member_comments WHERE member_id = /*memberId*/1 ;
DELETE FROM reply_comments WHERE member_id = /*memberId*/1 ;
/*IF !commentIdList.isEmpty()*/DELETE FROM comment WHERE comment_id IN /*commentIdList*/(100, 200) ;/*END*/

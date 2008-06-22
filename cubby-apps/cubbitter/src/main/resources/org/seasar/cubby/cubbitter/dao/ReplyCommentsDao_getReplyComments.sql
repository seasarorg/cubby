SELECT c.*, (fc.member_id IS NOT NULL) as favorite
FROM reply_comments rc
INNER JOIN comment_view c
ON c.comment_id = rc.comment_id
LEFT OUTER JOIN favorite_comments fc
ON fc.member_id = /*memberId*/1
AND fc.comment_id = rc.comment_id
WHERE rc.member_id = /*memberId*/1

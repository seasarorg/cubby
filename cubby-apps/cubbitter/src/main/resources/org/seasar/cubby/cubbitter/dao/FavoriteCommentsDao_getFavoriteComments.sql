SELECT c.*, true AS favorite
FROM favorite_comments fc
INNER JOIN comment_view c
ON c.comment_id = fc.comment_id
WHERE fc.member_id = /*memberId*/42
ORDER BY fc.comment_id DESC